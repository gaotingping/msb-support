package com.mvw.china.bus.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数标识
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiParam {

	/**
	 * 名称
	 * @return
	 */
	String value();
		
	/**
	 * 方法描述
	 * @return
	 */
	String desc() default "";
	
	/**
	 * 类型,默认是string
	 */
	Class<?> type() default String.class;
}
