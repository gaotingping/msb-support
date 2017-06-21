package com.mvw.china.bus.proxy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mvw.china.bus.dispatcher.ServiceDispatcher;
import com.mvw.china.bus.model.MsbResult;

/**
 * 服务委托代理:仅提供一种参考方式
 * @author gaotingping@cyberzone.cn
 */
public class ServiceDelegatingProxyServlet extends HttpServlet{

	private static final long serialVersionUID = -6690492906956742357L;

	private String targetBean=null;
	
	private ServiceDispatcher dispatcher;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceDelegatingProxyServlet.class);
	
	@Override
	public void init() throws ServletException {
		this.targetBean = this.getInitParameter("targetBean");
		this.getServletBean();
		
		if(logger.isInfoEnabled()){
			logger.info("proxy servlet init");
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String body=readResquestBody(request);
		MsbResult result=dispatcher.doService(body, request, response);
		printResponse(response, result);
	}

	private void getServletBean(){
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		this.dispatcher = (ServiceDispatcher) wac.getBean(targetBean);
	}
	
	private String readResquestBody(HttpServletRequest request) throws IOException {
		StringBuilder tmp=new StringBuilder();
		ServletInputStream in = request.getInputStream();
		byte [] buf=new byte[1024];
		int len=0;
		while((len=in.read(buf))>0){
			tmp.append(new String(buf,0,len));
		}
		return tmp.toString();
	}

	private void printResponse(HttpServletResponse response, MsbResult result) throws IOException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		String mes = JSON.toJSONString(result,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullBooleanAsFalse);
		out.write(mes.getBytes("UTF-8"));
		out.close();
	}
}
