package springbustest.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import springbustest.model.TestBean;
import springbustest.service.TestService;

/**
 * 服务接口测试
 * 
 */
@Component
public class TestServiceImpl implements TestService{
	
	public TestBean test1(){
		
		TestBean t=new TestBean();
		t.setId(1);
		t.setName("123");
		
		return t;
	}
	
	public TestBean test2(JSONObject args){
		
		TestBean t=new TestBean();
		t.setId(1);
		t.setName("456");
		
		return t;
	}
	
	public TestBean test3(String id,TestBean b) {
		
		TestBean t=new TestBean();
		t.setId(1);
		t.setName("789");
		
		return t;
    }
}
