package com.traffic.weizhang.strategy;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 抽象的策略
 * @author Administrator
 *
 */
public abstract class AbstractSuppplier {

	/**
	 * 根据不同的供应商执行不同的查询操作
	 */
	public abstract JSONObject executeQuery(JSONObject reqJsonBody,String interUrl);
	
	public abstract Map<String, String> executeQueryCities(String interUrl);
	
}
