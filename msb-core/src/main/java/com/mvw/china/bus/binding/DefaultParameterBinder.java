package com.mvw.china.bus.binding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.mvw.china.bus.annotation.ApiParam;

/**
 * 一个默认的参数绑定器实现
 */
public class DefaultParameterBinder implements ParameterBinder{

	/**
	 * 一个方法的参数是固定的，所以这块数据可缓存
	 */
	private static Map<String, List<ApiParam>> cacheParms = new ConcurrentHashMap<String,List<ApiParam>>();

	public List<ApiParam> getParamNames(String serviceCode,Method method) {
		
		List<ApiParam> params = cacheParms.get(serviceCode);
		
		/*不考虑并发处理，并发下也最多是多执行一次不会导致数据错误*/
		if(params==null){
			Annotation[][] pp = method.getParameterAnnotations();
			if(pp!=null){
				params=new ArrayList<ApiParam>();
				for(Annotation[] p:pp){
					for(Annotation p2:p){
						if(p2 instanceof ApiParam){
							ApiParam bp = (ApiParam)p2;
							params.add(bp);
						}
					}
				}
				if(!params.isEmpty()){
					cacheParms.put(serviceCode, params);
				}
			}
		}
		
		return params;
	}

	public Object[] getParamValues(Method method,List<ApiParam> paramNames,
			JSONObject args) {
		
		int size=paramNames.size();
		Object [] paramValues = new Object[size];
		for (int i = 0; i < size; i++) {
			ApiParam bp = paramNames.get(i);
			paramValues[i] = getInptVal(args,bp);
		}
		
		return paramValues;
	}
	
	private Object getInptVal(JSONObject args, ApiParam apiParam) {
		return args.getObject(apiParam.value(), apiParam.type());
	}
}
