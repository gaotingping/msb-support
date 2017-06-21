package com.mvw.china.bus.model;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 业务逻辑自定义
 * 
 * @author gaotingping@cyberzone.cn
 */
public class BizResult {

	private String flag = "true";

	private String error = "";

	private Object result;
	
	private Date stime=new Date();
	
	public BizResult() {
		super();
	}

	public BizResult(String error) {
		flag = "false";
		this.error = error;
	}

	public BizResult(Object data) {
		flag = "true";
		this.result = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public static MsbResult result(Object obj) {

		MsbResult result = new MsbResult();
		result.setOpFlag("true");
		result.setServiceResult(JSON.toJSONString(new BizResult(obj),
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullBooleanAsFalse));
		return result;
	}

	public static MsbResult failure(String error) {
		MsbResult result = new MsbResult();
		result.setOpFlag("true");
		
		result.setServiceResult(JSON.toJSONString(new BizResult(error)));
		
		return result;
	}

	public static MsbResult pageResult(Object data, int currentPage, int pageSize, int total) {
		
		JSONObject result = new JSONObject();

		result.put("data", data);
		result.put("currentPage", currentPage);
		result.put("pageSize", pageSize);
		result.put("total", total);
		
		return result(result);
	}
}
