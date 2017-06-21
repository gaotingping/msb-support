package org.msb.api;

import com.alibaba.fastjson.JSON;

import springbustest.model.TestBean;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		TestBean t = JSON.parseObject("{\"id\":\"123\",\"name\":\"123\"}",TestBean.class);
		System.out.println(t);
	}
}
