package com.mvw.china.bus.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法标识
 * 
 * @author gaotingping@cyberzone.cn
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiMethod {
	
	/**
	 * 方法编码
	 * 
	 * @return
	 */
	String value();
	
	/**
	 * 方法描述
	 * @return
	 */
	String desc() default "";
	
	/**
	 * 跳过参数的字段绑定
	 */
	boolean skipBP() default false;
}
