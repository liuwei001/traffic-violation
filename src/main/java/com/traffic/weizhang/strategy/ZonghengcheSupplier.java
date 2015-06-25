package com.traffic.weizhang.strategy;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.traffic.common.holder.PropertiesCfgHolder;
import com.traffic.common.utils.MD5Encrypt;
import com.traffic.common.utils.http.HttpClientUtils;

/**
 * 第一家供应商
 * @author Administrator
 *
 */
public class ZonghengcheSupplier extends AbstractSuppplier {

	private final Logger logger = Logger.getLogger(ZonghengcheSupplier.class);
	
	/**
	 * 查询
	 */
	@Override
	public JSONObject executeQuery(JSONObject reqJsonBody,String interUrl) {
		
		String citycode = reqJsonBody.getString("city");
		
		String appid = PropertiesCfgHolder.getProperty("weizhang.appid");
		String appkey = PropertiesCfgHolder.getProperty("weizhang.appkey");
		
		reqJsonBody.put("appid", appid);
		String carno = reqJsonBody.getString("carno");
		if(logger.isDebugEnabled()) {
			logger.debug("sign key : " + (citycode + carno + appkey));
		}
		String sign_md5 = MD5Encrypt.encrypt(citycode + carno + appkey,"UTF-8");
		if(logger.isDebugEnabled()) {
			logger.debug("sign md5 : " + sign_md5);
		}
		//设置签名参数
		reqJsonBody.put("sign", sign_md5);

		String respBody = HttpClientUtils.httpPost_JSONObject(interUrl, reqJsonBody);
		
		JSONObject respObj = JSON.parseObject(respBody);
		
		return respObj;
	}
	
}
