package com.traffic.weizhang.strategy;

import com.alibaba.fastjson.JSONObject;

public class SuppplierContext {

	private AbstractSuppplier supplier;
	
	public SuppplierContext(AbstractSuppplier supplier) {
		this.supplier = supplier;
	}
	
	/**
	 * 根据不同的供应商构建不同的请求对象
	 */
	public JSONObject executeQuery(JSONObject reqJsonBody,String interUrl) {
		return supplier.executeQuery(reqJsonBody,interUrl);
	}
}
