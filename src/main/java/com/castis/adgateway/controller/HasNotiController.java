package com.castis.adgateway.controller;


import com.castis.adgateway.controller.common.AbstractController;
import com.castis.adgateway.dto.response.v1_5.DescriptionResponse;
import com.castis.adgateway.service.CSISService;
import com.castis.adlib.define.Constants;
import com.castis.adlib.dto.TransactionID;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;


@RestController
@Slf4j
@RequiredArgsConstructor
public class HasNotiController extends AbstractController {

    private final CSISService csisService;


    @RequestMapping(value = "/VODRequestIDNotify", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<?> setVODRequestDescription(HttpServletRequest req) throws JAXBException {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST);

            csisService.processNotification(trId, req);

            DescriptionResponse desc = new DescriptionResponse("OK");

            result = new ResponseEntity<>(desc, getHeaders(desc), HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DescriptionResponse("FAIL"));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }





}
