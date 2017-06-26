package com.mvw.china.bus.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mvw.china.bus.constants.ErrorConstants;
import com.mvw.china.bus.executor.DispatcherExecutor;
import com.mvw.china.bus.model.MsbResult;

/**
 * 服务转发器，就是一个Controller
 * 负责请求的转发，参数解码和做初步验证
 * 真正的转发处理请参阅 <code>DispatcherExecutor</code>
 * 
 * 它其实是一个servlet,为了注入相关服务类
 * 本框架中设置了前置委托代理处理器
 */
public class DefaultServiceDispatcher implements ServiceDispatcher{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultServiceDispatcher.class);

	private DispatcherExecutor dispatcherExecutor;
	
	private String serviceModule;
	
	@Override
	public MsbResult doService(String body, HttpServletRequest request,HttpServletResponse response) {

		try{
			
			//step1:获得请求参数
			JSONObject json=JSON.parseObject(body);
			
			//step2:输入初步验证
			String tmpError=checkParams(json);
			
			//step3:交给转发执行器
			if(tmpError==null){
				String serviceNumber = json.getString("serviceNumber");
				JSONObject args = json.getJSONObject("args");
				return dispatcherExecutor.execute(serviceNumber, args);
			}else{
				return MsbResult.failure(tmpError);
			}

		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return MsbResult.failure(ErrorConstants.SYSTEM_ERROR);
		}
		
	}

	private String checkParams(JSONObject json) {
		
		String serviceModule = json.getString("serviceModule");

		if (!serviceModule.equals(serviceModule)) {
			return ErrorConstants.SERVICE_MODULE_ERROR;
		}
		
		String serviceNumber = json.getString("serviceNumber");
		
		if (serviceNumber == null || "".equals(serviceNumber)) {
			return ErrorConstants.SERVICE_CODE_EMPTY;
		}
		
		return null;
	}

	//setter getter
	public DispatcherExecutor getDispatcherExecutor() {
		return dispatcherExecutor;
	}

	public void setDispatcherExecutor(DispatcherExecutor dispatcherExecutor) {
		this.dispatcherExecutor = dispatcherExecutor;
	}

	public String getServiceModule() {
		return serviceModule;
	}

	public void setServiceModule(String serviceModule) {
		this.serviceModule = serviceModule;
	}
}
