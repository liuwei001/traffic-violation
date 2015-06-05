package com.traffic.weizhang.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.traffic.common.base.BaseController;
import com.traffic.common.configuration.PropertiesCfgHolder;
import com.traffic.common.enumcode.ResultCodeEnum;
import com.traffic.common.message.ResponseMessage;
import com.traffic.common.utils.MD5Encrypt;
import com.traffic.common.utils.http.HttpClientUtils;

/**
 * 交通违章信息controller
 * @author Administrator
 *
 */
@Controller
public class WeiZhangInfoController extends BaseController {

	private final Logger logger = Logger.getLogger(WeiZhangInfoController.class);
	
	/**
	 * 查询违章列表
	 * @return
	 */
	@RequestMapping( value = "/querylist",method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryList(HttpServletRequest request) {
		JSONObject reqJsonBody = (JSONObject)request.getAttribute("reqBody");
		String reqUrl = PropertiesCfgHolder.getProperty("weizhang.query");
		if(StringUtils.isEmpty(reqUrl)) {
			logger.error("cfg.properties属性文件中没有配置违章查询接口地址,配置参数为：violation.query");
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
		try {
			reqJsonBody.put("appid", PropertiesCfgHolder.getProperty("weizhang.appid"));
			
			String appkey = PropertiesCfgHolder.getProperty("weizhang.appkey");
			String city = reqJsonBody.getString("city");
			String carno = reqJsonBody.getString("carno");
			String sign = MD5Encrypt.encrypt(city + carno + appkey);
			//设置签名参数
			reqJsonBody.put("sign", sign);
			
			String respBody = HttpClientUtils.httpPost_JSONObject(reqUrl, reqJsonBody);
			return JSON.parseObject(respBody, ResponseMessage.class);
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
		
	}
}
