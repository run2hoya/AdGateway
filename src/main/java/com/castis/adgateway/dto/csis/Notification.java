package com.castis.adgateway.dto.csis;


import com.castis.adgateway.dto.FileWritable;
import com.castis.adlib.dto.TransactionID;
import com.castis.adlib.util.CiFileUtil;
import com.castis.adlib.util.MyJAXBContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.*;
import java.io.File;
import java.util.Calendar;


@XmlRootElement(name="Notification")
@XmlType(propOrder={})
public class Notification implements Serializable , FileWritable  //extends MyXML
{
	static final long serialVersionUID = -7758504050245308219L;
	static final Log	log = LogFactory.getLog( Notification.class );
	static final JAXBContext jaxbContext = MyJAXBContext.initContext(Notification.class);
	
	String		vodRequestId;
	Category	category;
	Asset		asset;
	User		user;
	Region		region;
	Product		product;
	WatchInfo	watchInfo;
	
	//----------------------------------------------------------
	// Getter/setter methods
	
	@XmlElement(name="VODRequestId")
	public String getVodRequestId() {
		return vodRequestId;
	}
	public void setVodRequestId( String id ) {
		vodRequestId = id;
	}

	@XmlElement(name="Category")
	public Category getCategory() {
		return category;
	}
	public void setCategory( Category cat ) {
		category = cat;
	}

	@XmlElement(name="Asset")
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	@XmlElement(name="User")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@XmlElement(name="Region")	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}

	@XmlElement(name="Product")
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@XmlElement(name="WatchInfo")
	public WatchInfo getWatchInfo() {
		return watchInfo;
	}
	public void setWatchInfo(WatchInfo watchInfo) {
		this.watchInfo = watchInfo;
	}

	// - - - - - - - - - - - - - - - -
	// Public methods
	
	
	@Override
	public String toString() {
		return "VodRequestDesc [vodRequestId=" + vodRequestId + ", category="
				+ category + ", asset=" + asset + ", user=" + user
				+ ", region=" + region + ", product=" + product
				+ ", watchInfo=" + watchInfo + "]";
	}
	
	@Override
	public String getID() {
		return vodRequestId;
	}
	
	@Override
	public boolean writeFile(String fileDir, TransactionID trID) {
		if(fileDir.isEmpty())	return false;	//디렉토리 설정하지 않으면 파일을 남기지 않도록했음.
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try
		{
			String currentHourDir = CiFileUtil.createCurrentDirectory(fileDir, Calendar.HOUR_OF_DAY);	//디렉토리 생성
			String fileFullPath = currentHourDir + vodRequestId + ".xml";
			
			marshaling(bos);
			boolean result = CiFileUtil.writeFileLog(fileFullPath, bos.toString(), false);
			if(result)
				log.info(trID + fileFullPath + " write file - OK");
			return result;
		}
		catch(Exception e)
		{
			log.error(trID + "fileDir : " + fileDir + ", vodRequestId : " + vodRequestId  , e );
			return false;
		} finally {
			try { 
				bos.close(); 	bos = null;
			} catch (IOException e) {}
		}
	}
	
	public static Notification unmarshaling(File file) throws JAXBException
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Notification req = (Notification) unmarshaller.unmarshal(file);
		return req;
	}

	public static Notification unmarshaling(String value) throws JAXBException
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Notification req = (Notification) unmarshaller.unmarshal(new StringReader(value));
		return req;
	}
	
	public static Notification unmarshaling(InputStream xmlInput) throws JAXBException 
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Notification req = (Notification) unmarshaller.unmarshal(xmlInput);
		return req;
	}
	
	public void marshaling(OutputStream xmlOuput) throws JAXBException
	{
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty( "jaxb.formatted.output", true  );
		
		marshaller.marshal(this, xmlOuput);
	}
	
}