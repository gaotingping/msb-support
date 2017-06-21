package com.mvw.china.bus.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvw.china.bus.model.MsbResult;

/**
 * 服务转发器
 */
public interface ServiceDispatcher{
	
	public MsbResult doService(String body,HttpServletRequest request, HttpServletResponse response);

	public void setServiceModule(String serviceModule);

	public boolean tokenVerify(String token);
}
