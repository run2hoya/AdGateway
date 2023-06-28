package com.castis.adgateway.service;


import com.castis.adgateway.common.Properties;
import com.castis.adgateway.dto.ResNotificationInfoDto;
import com.castis.adgateway.dto.csis.Notification;
import com.castis.adgateway.model.Description;
import com.castis.adgateway.repository.DescriptionRepository;
import com.castis.adlib.dto.TransactionID;
import com.castis.adlib.util.HttpConnectorUtil;
import com.castis.adlib.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@Data
public class CSISService {

    private final Properties properties;
    private final DescriptionRepository descriptionRepository;



    public void processNotification(TransactionID trId, HttpServletRequest request) throws Exception {

        String descriptionStr = null;
        Notification notification = null;

        try {
            //descriptionStr = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);

            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "EUC-KR"));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            descriptionStr = stringBuilder.toString();

            log.info("{} description = {}", trId, descriptionStr);
            notification = Notification.unmarshaling(descriptionStr);

            if(notification == null)
                throw new JAXBException("received NotificationData is null");

        } catch (JAXBException e) {
            log.error(trId + "request data parse error original XML : " + descriptionStr);
            throw e;
        }

        //1. db에 저장
        saveDescription(trId, notification);
        //2. CSIS에 저장
        sendDesc(trId, descriptionStr);
    }

    public ResNotificationInfoDto getDescriptionInfo(TransactionID trId, String categoryId, String soId, String stbId, String provider, String assetId) {

        try {
            StringBuilder requestUrl = new StringBuilder();
            requestUrl.append(properties.getAssetDispatcherUrl()).append("/description/info?categoryId=");
            requestUrl.append(categoryId).append("&stbId=").append(stbId).append("&provider=").append(StringUtil.encode(provider)).append("&assetId=").
                    append(StringUtil.encode(assetId)).append("&soId=").append(soId);
            log.info("{} AssetDispatcher로 description 정보를 요청합니다. request = {}", trId, requestUrl);

            URL url = new URL(requestUrl.toString());
            InputStream is = HttpConnectorUtil.getResponse(url, properties.getAssetDispatcherServerTimeOut());

            if(is != null) {
                return (ResNotificationInfoDto) HttpConnectorUtil.getObjFromJson(is, ResNotificationInfoDto.class);
            } else
                log.error(trId + "InputStream is null");
        } catch (Exception e) {
            log.error(trId + "" , e);
        }

        return null;
    }

    public void saveDescription(TransactionID trId, Notification notification) {
        try {
            Description description = new Description(notification);

            String categoryId = null, soId = null, provider = null, assetId = null, stbId = null, providerId = null;
            categoryId = (StringUtil.isNull(notification.getCategory())) ?  "" : notification.getCategory().getCategoryId();
            soId = (StringUtil.isNull(notification.getRegion())) ?  "" : notification.getRegion().getExternalSoId();
            provider = (StringUtil.isNull(notification.getAsset())) ?  "" : notification.getAsset().getProvider();
            assetId = (StringUtil.isNull(notification.getAsset())) ?  "" : notification.getAsset().getAssetId();
            stbId = (StringUtil.isNull(notification.getUser())) ?  "" : notification.getUser().getStbId();
            providerId = (StringUtil.isNull(notification.getAsset())) ?  "" : notification.getAsset().getProviderId();

            ResNotificationInfoDto resNotificationInfoDto = getDescriptionInfo(trId, categoryId, soId, stbId, provider, providerId + "|" + assetId);
            if(resNotificationInfoDto != null) {
                description.setCategory(resNotificationInfoDto.getCategoryInfo());
                description.setRegionCode(resNotificationInfoDto.getRegionCode());
                description.setSeries(resNotificationInfoDto.getSeries());
                description.setContentProvider(resNotificationInfoDto.getProvider());
                descriptionRepository.save(description);
                log.info("{} description 정보 저장 성공 {}", trId, description);
            } else
                log.error("{} NotificationInfo 정보를 가져 오는데 실패 했습니다.", trId);
        } catch (Exception e) {
            log.error(trId + "" , e);
        }
    }

    public void sendDesc(TransactionID trId, String body) {

        try {
            String requestUrl = properties.getCsisUrl() + "/VODRequestIDNotify";
            URL url = new URL(requestUrl);
            log.info("{} CSIS로 description 정보를 전달 합니다. request = {}", trId, requestUrl);

            HttpURLConnection urlConn = null;
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setRequestMethod( "POST" );
            urlConn.setDoInput( true );
            urlConn.setDoOutput( true );
            urlConn.setUseCaches( false );
            urlConn.setConnectTimeout(properties.getCsisServerTimeOut());
            urlConn.setReadTimeout(properties.getCsisServerTimeOut());
            urlConn.setRequestProperty("Content-Type", "text/xml;");
            DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "EUC-KR"));
            writer.write(body);
            writer.close();
            wr.close();

            if(urlConn.getResponseCode() == HttpURLConnection.HTTP_OK){
                log.info(trId + HttpConnectorUtil.convertStreamToString(urlConn.getInputStream()));
            } else {
                log.error("External System({}}) tomcat error : response code[{}}], response message[{}}]",
                        url.getPath(), urlConn.getResponseCode(), urlConn.getResponseMessage());
            }

        } catch (Exception e) {
            log.error(trId + "" , e);
        }
    }
}
