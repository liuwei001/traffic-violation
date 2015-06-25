/* 
 * Copyright (c) 2015, QUANRONG E-COMMDERCE LTD. All rights reserved.
 */
package com.traffic.common.utils.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.traffic.common.utils.MD5Encrypt;

/**
 * 描述：
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015年4月29日      vmp         Create
 * ****************************************************************************
 * </pre>
 * 
 * @author vmp
 * @since 1.0
 */
public class HttpClientUtils {

	public static final Logger logger = Logger.getLogger(HttpClientUtils.class);

	private static Map<String, String> headers = new HashMap<String, String>();

	static {
//		headers.put("User-Agent",
//				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
//		headers.put("Accept-Language", "zh-cn,zh;q=0.5");
//		headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//		headers.put(
//				"Accept",
//				" image/gif, image/x-xbitmap, image/jpeg, "
//						+ "image/pjpeg, application/x-silverlight, application/vnd.ms-excel, "
//						+ "application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
//		headers.put("Accept-Encoding", "gzip, deflate");
	}

	/**
	 * 异常或者没拿到返回结果的情况下,result为""
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String httpPost(String url, String paramvalue) {
		logger.info("httpPost URL [" + url + "] start ");
		if(logger.isDebugEnabled()) {
			logger.debug("httpPost body :" + paramvalue);
		}
		logger.info("httpPost body :" + paramvalue);
		HttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";

		try {
			httpclient = HttpConnectionManager.getHttpClient();
			// 设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");  
			httpPost = new HttpPost(url);
			// 设置各种头信息
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
			
			StringEntity stringEntity = new StringEntity(paramvalue, "UTF-8");
            httpPost.setEntity(stringEntity);
            
			// 设置连接超时时间
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),40000);
			// 设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(),200000);
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpStatus ERROR" + "Method failed: "
						+ response.getStatusLine());
				return "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					result = new String(bytes, "UTF-8");
				} else {
					logger.error("httpPost URL [" + url
							+ "],httpEntity is null.");
				}
				return result;
			}
		} catch (Exception e) {
			logger.error("httpPost URL [" + url + "] error, ", e);
			return "";
		}
	}
	
	/**
	 * map参数 post请求
	 * @param url
	 * @param mapParam
	 * @return
	 */
	public static String httpPost_JSONObject(String url, JSONObject reqParam) {
		logger.debug("httpPost URL [" + url + "] start ");
		logger.debug("httpPost param :" + reqParam.toJSONString());
		HttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";

		try {
//			httpclient = HttpConnectionManager.getHttpClient();
			httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");  
			httpPost = new HttpPost(url);
			// 设置各种头信息
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
			
			// 创建参数队列    
	        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
	        for (String key : reqParam.keySet()) {
	        	formparams.add(new BasicNameValuePair(key, (String)reqParam.get(key)));
        	}
	        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
	        httpPost.setEntity(uefEntity);  
            
			// 设置连接超时时间
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),40000);
			// 设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(),200000);
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpStatus ERROR" + "Method failed: "
						+ response.getStatusLine());
				return "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					result = new String(bytes, "UTF-8");
				} else {
					logger.error("httpPost URL [" + url
							+ "],httpEntity is null.");
				}
				logger.debug("httpPost response:" + result);
				return result;
			}
		} catch (Exception e) {
			logger.error("httpPost URL [" + url + "] error, ", e);
			return "";
		}
	}
	
	/**
	 * 异常或者没拿到返回结果的情况下,result为""
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String httpGet(String url) {
		logger.info("httpGet URL [" + url + "] start ");
		HttpClient httpclient = null;
		HttpGet httpGet = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		try {
			httpclient = HttpConnectionManager.getHttpClient();
			// 设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");  
			httpGet = new HttpGet(url);
			// 设置各种头信息
			for (Entry<String, String> entry : headers.entrySet()) {
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
			response = httpclient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpStatus ERROR" + "Method failed: "
						+ response.getStatusLine());
				return "";
			} else {
				entity = response.getEntity();
				if (null != entity) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					result = new String(bytes, "UTF-8");
				} else {
					logger.error("httpGet URL [" + url
							+ "],httpEntity is null.");
				}
				return result;
			}
		} catch (Exception e) {
			logger.error("httpGet URL [" + url + "] error, ", e);
			return "";
		} 
	}
	
	
	public static void main(String[] args) {
		//将cst时间格式转换为GMT时间格式
		DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = format.format(new Date());
		
		String s = "license_plate_num=%E7%B2%A4BA804D&engine_num=C32764&body_num=064484&city_pinyin=shenzhen";
		//计算签名
		String SIGNATURE = MD5Encrypt.encrypt("POST&/v3/violations&"+date+"&"+s.length()+"&"+ MD5Encrypt.encrypt("uTIYrJn6vJTyt1ztBNbqQQDexDjpAM4m"));
		//设置请求头
		headers.put("Host", "api.buding.cn");
		headers.put("Authorization", "dLSQ1ZjK7exqlwqx:" + SIGNATURE);
		headers.put("Date", date);
		
		JSONObject reqParam = new JSONObject();
		reqParam.put("license_plate_num", "粤BA804D");
		reqParam.put("engine_num", "C32764");
		reqParam.put("body_num", "064484");
		reqParam.put("city_pinyin", "shenzhen");
		String respBody = HttpClientUtils.httpPost_JSONObject("http://api.buding.cn/v3/violations", reqParam);
		System.out.println(JSONObject.parse(respBody));
	}
	
}
