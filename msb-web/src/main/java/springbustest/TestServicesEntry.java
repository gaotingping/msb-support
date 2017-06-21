//package springbustest;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.mvw.china.bus.dispatcher.ServiceDispatcher;
//import com.mvw.china.bus.model.MsbResult;
//
///**
// * 和spring mvc集成演示例子
// * 
// * 不用再配置自己的servlet入口，直接使用springMvcSupport即可
// * 即自己提供入口类，如下样例(仅展示未配置springmvc环境测试)
// */
//@Controller
//public class TestServicesEntry {
//
//	private ServiceDispatcher serviceDispatcher;
//	
//	@RequestMapping(method = RequestMethod.POST, value = "/services2")
//	@ResponseBody
//	public MsbResult doService(@RequestBody String body,HttpServletRequest request,HttpServletResponse response) {
//		return serviceDispatcher.doService(body, request, response);
//	}
//
//	public ServiceDispatcher getServiceDispatcher() {
//		return serviceDispatcher;
//	}
//
//	public void setServiceDispatcher(ServiceDispatcher serviceDispatcher) {
//		this.serviceDispatcher = serviceDispatcher;
//	}
//}
