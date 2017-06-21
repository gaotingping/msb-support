package com.mvw.china.bus.binding;

import java.lang.reflect.Method;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.mvw.china.bus.annotation.ApiParam;

//参数绑定约定
public interface ParameterBinder {

     
	/**
	 * 获得参数上的所有注解
	 * 
	 * @param serviceCode  服务编码
	 * @param method       方法
	 * @return
	 * 
	 * 2016年2月26日
	 */
	public List<ApiParam> getParamNames(String serviceCode,Method method);


	/**
	 * FIXME 
	 * 可以考虑，注入request,response
	 * 根据参数名称获得参数对应的输入值
	 * 
	 * @param method       方法
	 * @param paramNames   参数名称
	 * @param args         输入的参数集合
	 * @param request 
	 * @param response      
	 * @return
	 * 
	 * 2016年2月26日
	 */
	public Object[] getParamValues(Method method,List<ApiParam> paramNames,JSONObject args);
}
