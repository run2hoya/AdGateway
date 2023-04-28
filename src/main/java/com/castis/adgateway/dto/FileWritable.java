package com.castis.adgateway.dto;


/*
 * FileWrite의 기능을 가진 Interface
 * 파일을 쓸수 있는 기능을 제공 한다.
 * 
 * @author Fxpark
 */

import com.castis.adlib.dto.TransactionID;

public interface FileWritable {
	
	/*
	 * 파일을 남길 file Directory와 로그에 기록할 transactionID를 파라미터로 받는다. 
	 */
	public abstract boolean writeFile(String fileDir, TransactionID trID);
	public abstract String getID();
}
