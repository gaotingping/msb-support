package springbustest.service;

import com.alibaba.fastjson.JSONObject;
import com.mvw.china.bus.annotation.ApiMethod;
import com.mvw.china.bus.annotation.ApiParam;
import com.mvw.china.bus.annotation.ApiService;

import springbustest.model.TestBean;

/**
 * 服务接口测试
 * 
 */
@ApiService("测试接口")
public interface TestService {

	//没有参数
	@ApiMethod(value="001",desc="空参数测试方法")
	public TestBean test1();
	
	//注入原生json args
	@ApiMethod(value="002",desc="原生参数测试方法",skipBP=true)
	public TestBean test2(JSONObject args);
	
	//注入自己需要的参数
	@ApiMethod(value="003",desc="自定义参数")
	public TestBean test3(
			@ApiParam(value="id",desc="主键") String id,
			@ApiParam(value="b",type=TestBean.class) TestBean b);
}
