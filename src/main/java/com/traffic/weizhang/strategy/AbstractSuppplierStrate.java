package com.traffic.weizhang.strategy;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.traffic.weizhang.entity.Supplier;

/**
 * 抽象的策略
 * @author Administrator
 *
 */
public abstract class AbstractSuppplierStrate {

	/**
	 * 根据不同的供应商执行不同的查询操作
	 */
	public abstract JSONObject executeQuery(JSONObject reqJsonBody,Supplier supplier);
	
	public abstract Map<String, String> executeQueryCities(String interUrl);
	
}
