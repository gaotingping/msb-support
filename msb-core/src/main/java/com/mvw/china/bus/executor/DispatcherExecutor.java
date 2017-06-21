package com.mvw.china.bus.executor;

import com.alibaba.fastjson.JSONObject;
import com.mvw.china.bus.model.MsbResult;

//转发执行约定
public interface DispatcherExecutor {
	
	public void init();
	
	public void destroy();
	
	public MsbResult execute(String serviceCode, JSONObject args);
}
