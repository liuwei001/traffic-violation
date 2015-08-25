package com.traffic.weizhang.strategy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.traffic.common.holder.PropertiesCfgHolder;
import com.traffic.common.utils.DateUtil;
import com.traffic.common.utils.MD5Encrypt;
import com.traffic.common.utils.http.HttpClientUtils;
import com.traffic.weizhang.entity.Result;

/**
 * 第二家供应商
 * @author Administrator
 *
 */
public class BudingSupplier extends AbstractSuppplier {
	
	private final Logger logger = Logger.getLogger(BudingSupplier.class);

	@Override
	public JSONObject executeQuery(JSONObject reqJsonBody, String interUrl) {
		
		//将cst时间格式转换为GMT时间格式
		DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = format.format(new Date());
		String carno = reqJsonBody.getString("carno");
		String engineno = reqJsonBody.getString("engineno");
		String classno = reqJsonBody.getString("classno");
		String citycode = reqJsonBody.getString("city");
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("license_plate_num=")
					.append(URLEncoder.encode(carno, "utf-8"))
					.append("&engine_num=").append(engineno)
					.append("&body_num=").append(classno)
					.append("&city_pinyin=").append(citycode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			JSONObject resultJsonObj = new JSONObject();
			resultJsonObj.put("resultCode", "-1");
			resultJsonObj.put("resultMsg", "系统出现异常");
			return resultJsonObj;
		}
		//计算签名
		String SIGNATURE = MD5Encrypt.encrypt("POST&/v3/violations&"+date+"&"+builder.toString().length()+"&"+ MD5Encrypt.encrypt(PropertiesCfgHolder.getProperty("buding.secret")));
		
		//设置请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Host", "api.buding.cn");
		headers.put("Authorization", PropertiesCfgHolder.getProperty("buding.appkey")+":" + SIGNATURE);
		headers.put("Date", date);
		System.out.println(JSON.toJSONString(headers));
		JSONObject reqParam = new JSONObject();
		reqParam.put("license_plate_num", carno);
		reqParam.put("engine_num", engineno);
		reqParam.put("body_num", classno);
		reqParam.put("city_pinyin", citycode);
		String respBody = HttpClientUtils.httpPost_JSONObject(interUrl,headers, reqParam);
		Map<String,Object> respMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(respBody)) {
			JSONObject respJsonObj = JSONObject.parseObject(respBody);
			if("ok".equals(respJsonObj.getString("vehicle_status"))) {
				respMap.put("resultCode", "0");
				JSONArray array  = respJsonObj.getJSONArray("violations");
				List<Result> resultList = new ArrayList<Result>();
				if(array != null && array.size() > 0) {
					for(int i = 0;i < array.size(); i++) {
						JSONObject jsonObj = array.getJSONObject(i);
						Result result = new Result();
						result.setAct(jsonObj.getString("violation_type"));
						result.setArea(jsonObj.getString("address"));
						result.setDate(StringUtils.isNotEmpty(jsonObj.getString("time")) ? DateUtil.getDateFromString(jsonObj.getString("time")) : null);
						result.setFen(jsonObj.getInteger("point") >= 0 ? String.valueOf(jsonObj.getInteger("point")) : "未知");
						result.setMoney(jsonObj.getInteger("fine") >= 0 ? String.valueOf(jsonObj.getInteger("fine")) : "未知");
						resultList.add(result);
					}
				}
				
				respMap.put("result", resultList);
			}
			return (JSONObject)JSONObject.toJSON(respMap);
		} else {
			JSONObject resultJsonObj = new JSONObject();
			resultJsonObj.put("resultCode", "-1");
			resultJsonObj.put("resultMsg", "系统出现异常");
			return resultJsonObj;
		}
		
	}

	
	 public static void main(String[] args) {
	
		 BudingSupplier supplier = new BudingSupplier();
		 JSONObject reqJsonBody = new JSONObject();
		reqJsonBody.put("carno", "粤BA804D");
		 reqJsonBody.put("engineno", "C32764");
		 reqJsonBody.put("classno", "064484");
		 reqJsonBody.put("city", "shenzhen");
		 
			
		 //{"carType":"02","engineno":"C32764","carno":"粤BA804D","classno":"064484","mobile":"18675574642","city":"GD_ShenZhen"}
		 
		 System.out.println(supplier.executeQuery(reqJsonBody, "http://api.buding.cn/v3/violations"));
	}
}
