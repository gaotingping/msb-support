package com.mvw.china.bus.model;

import java.io.Serializable;

/**
 * 总线数据协议
 * 
 * @author gaotingping@cyberzone.cn
 */
public class MsbResult implements Serializable {

	private static final long serialVersionUID = 3525295025312897105L;

	private String opFlag = "false";

	private String errorMessage = "";

	private String serviceResult = "";

	private String timestamp = "";

	public String getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getServiceResult() {
		return serviceResult;
	}

	public void setServiceResult(String serviceResult) {
		this.serviceResult = serviceResult;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public static MsbResult failure(String error) {
		
		MsbResult result = new MsbResult();
		
		result.setOpFlag("false");
		result.setErrorMessage(error);
		
		return result;
	}
}