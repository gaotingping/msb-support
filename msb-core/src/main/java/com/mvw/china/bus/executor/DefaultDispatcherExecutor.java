package com.mvw.china.bus.executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mvw.china.bus.annotation.ApiMethod;
import com.mvw.china.bus.annotation.ApiParam;
import com.mvw.china.bus.binding.ParameterBinder;
import com.mvw.china.bus.constants.ErrorConstants;
import com.mvw.china.bus.model.BizResult;
import com.mvw.china.bus.model.MsbResult;
import com.mvw.china.common.ScannerUtils;

/**
 * 转发执行器默认实现
 * 
 * @author gaotingping@cyberzone.cn
 */
@Component
public class DefaultDispatcherExecutor implements DispatcherExecutor,ApplicationContextAware{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultDispatcherExecutor.class);
	
	private static Map<String, Object> flyweights = new HashMap<String, Object>();

	private static Map<String, Method> methods = new HashMap<String, Method>();
	
	private List<String> packes=null;
	
	private ParameterBinder parameterBinder;

	private ApplicationContext applicationContext=null;
	
	public void init(){
		try{
			if(flyweights.isEmpty()){
				for (Class<?> clazz : ScannerUtils.getServiceEntry(packes)) {
					for (Method method : clazz.getDeclaredMethods()) {
						ApiMethod serviceCode = method.getAnnotation(ApiMethod.class);
						if(serviceCode!=null){
							flyweights.put(serviceCode.value(), applicationContext.getBean(method.getDeclaringClass()));
							methods.put(serviceCode.value(), method);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void destroy() {
		flyweights.clear();
		methods.clear();
	}
	
	public MsbResult execute(String serviceCode, JSONObject args) {
		
		Object instance = flyweights.get(serviceCode);
		Method method = methods.get(serviceCode);
		
		try {
			
			 //method not find
			 if(method==null){
				 return  MsbResult.failure(ErrorConstants.SERVICE_CODE_NOTFIND);
			 }
			 
			 //param is empty
			 Class<?>[] pts = method.getParameterTypes();
			 if(pts==null || pts.length==0){
				 return doExecute(method,instance,null);
			 }
			 
			 //args is empty
			 if(args==null || args.isEmpty()){
				return MsbResult.failure(ErrorConstants.REQUIRED_NOT_SET);
			 }
		
			 //skip binding param
			 ApiMethod apiMethod = method.getAnnotation(ApiMethod.class);
			 if(apiMethod.skipBP()){
				 return doExecute(method,instance,new Object[]{args});
			 }
			 
			 //binding param
			 List<ApiParam> paramNames = parameterBinder.getParamNames(serviceCode,method);
			 if(paramNames!=null && !paramNames.isEmpty()){ 
				 Object[] paramValues=parameterBinder.getParamValues(method,paramNames,args);
				 return doExecute(method,instance,paramValues);
			 }else{
				 return doExecute(method,instance,new Object[]{args});
			 }
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	private MsbResult doExecute(Method method, Object instance, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try{
			Object result = method.invoke(instance,args);
			return BizResult.result(result);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return BizResult.failure(e.getMessage());
		}
	}

	//setter getter
	public ParameterBinder getParameterBinder() {
		return parameterBinder;
	}


	public void setParameterBinder(ParameterBinder parameterBinder) {
		this.parameterBinder = parameterBinder;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	public List<String> getPackes() {
		return packes;
	}

	public void setPackes(List<String> packes) {
		this.packes = packes;
	}
}
