package com.traffic.common.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.traffic.common.configuration.PropertiesCfgHolder;
import com.traffic.common.enumcode.ResultCodeEnum;
import com.traffic.common.message.ResponseMessage;
import com.traffic.common.utils.http.HttpClientUtils;

/**
 * @Description:公共的Controller类
 * @Copyright Copyright 2014-2015 traffic Tech. Co. Ltd. All Rights
 *            Reserved.<BR>
 * @author： liuwei <BR>
 * @Time： 2015年1月27日 <BR>
 * @version 1.0 <BR>
 */
public class BaseController {
	
	private static Logger logger = Logger.getLogger(BaseController.class);
	
	/**
	 * 请求拦截，将所有request请求的body信息转换成JsonObject对象存入request作用域的reqBody属性中
	 *initReqBody  
	 *@param request
	 *@param response
	 *@throws UnsupportedEncodingException
	 *@throws IOException
	 */
	@ModelAttribute
	protected void initReqBody(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException
	{
		// 解析HTTP请求BODY
		JSONObject bodyJsonObj = null;
		// 将传过来的json数据打包成一个bufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) 
		{
			sb.append(line);
			sb.append("\r\n");
		}
		br.close();
		if(StringUtils.isEmpty(sb.toString()))
		{
		  sb.append("{}");
		}
		bodyJsonObj = JSONObject.parseObject(sb.toString());
		if (logger.isDebugEnabled())
		{
			logger.debug("\r\n\r\n请求url:"+request.getRequestURI()+"\r\n请求body:"+bodyJsonObj.toString());
		}
		request.setAttribute("reqBody", bodyJsonObj);
	}
	
	/**
	 *统一的异常返回格式
	 *getResponseMsg_failed  
	 *@param returnCode
	 *@param returnInfo
	 *@return
	 */
	protected ResponseMessage getResponseMsg_failed(ResultCodeEnum codeEnum) {
		ResponseMessage resMsg = new ResponseMessage();
		resMsg.setResultCode(codeEnum.getResultCode());
		resMsg.setResultMsg(codeEnum.getResultMsg());
		if (logger.isDebugEnabled()) {
			logger.debug("\r\n响应body:" + JSONObject.toJSONString(resMsg));
		}
		return resMsg;
	}
	
	/**
	 * 统一的成功返回格式
	 *getResponseMsg_success  
	 *@param object
	 *@return
	 */
	protected ResponseMessage getResponseMsg_success(Object object) {
		ResponseMessage resMsg = new ResponseMessage();
		resMsg.setResult(object);
		if (logger.isDebugEnabled()) {
			logger.debug("\r\n响应body:" + JSONObject.toJSONString(resMsg));
		}
		return resMsg;
	}
	
	/**
	 * 公共http透传方法
	 *httpRequestToTrans  
	 *@param configRequestUrlKey 配置的请求地址
	 *@return
	 */
	protected ResponseMessage httpRequestToTrans(String configRequestUrlKey,String reqBody) {
		String coreUrl = PropertiesCfgHolder.getProperty(configRequestUrlKey);
		
		if(logger.isDebugEnabled()) {
			logger.debug("requestUrl = " + configRequestUrlKey);
		}
		
		if(StringUtils.isBlank(coreUrl)) {
			ResponseMessage resMsg = new ResponseMessage();
			resMsg.setResultCode(ResultCodeEnum.REQUEST_PARAM_ISNULL.getResultCode());
			resMsg.setResultMsg("core.addRelate.url is null");
			return resMsg;
		}
		try {
			String resBody = HttpClientUtils.httpPost(coreUrl, reqBody);
			return JSON.parseObject(resBody, ResponseMessage.class);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			ResponseMessage resMsg = new ResponseMessage();
			resMsg.setResultCode(ResultCodeEnum.SYSTEM_EXCEPTION.getResultCode());
			resMsg.setResultMsg(e.getMessage());
			return resMsg;
		}
	}
}
