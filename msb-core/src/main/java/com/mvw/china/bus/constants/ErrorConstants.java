package com.mvw.china.bus.constants;

/**
 * 逻辑错误常量
 */
public @interface ErrorConstants {

	/**
	 * 服务模块错误
	 */
	String SERVICE_MODULE_ERROR="serviceModuleError";
	
	/**
	 * 服务编码错误
	 */
	String SERVICE_CODE_EMPTY="serviceCodeEmpty";
	
	/**
	 * 服务方法没有发现
	 */
	String SERVICE_CODE_NOTFIND="serviceMethodNotfind";

	/**
	 * 参数格式错误
	 */
	String PARAM_NOT_JSON = "paramNotJson";
	
	/**
	 * 系统错误
	 */
	String SYSTEM_ERROR="systemError";

	/**
	 * 参数错误
	 */
	String REQUIRED_NOT_SET = "requiredNotSet";
}
