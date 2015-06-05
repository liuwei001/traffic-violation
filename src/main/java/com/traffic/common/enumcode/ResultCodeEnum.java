package com.traffic.common.enumcode;

/**
 * 返回码的枚举信息
 * @Description:TODO
 * @Copyright Copyright 2014-2015 traffic Tech. Co. Ltd. All Rights Reserved.<BR>
 * @author： liuwei <BR>
 * @Time： 2015年2月3日 <BR>
 * @version 1.0.0 <BR>
 */
public enum ResultCodeEnum {
	
	SYSTEM_EXCEPTION("1000001","服务器异常"),
	
	REQUEST_PARAM_ISNULL("1010001","请求参数为null"),
	REQUEST_PARAM_ERROR("1010002","请求参数格式化错误");

	/**
	 * 返回编码
	 */
	private String resultCode;
	
	/**
	 * 返回信息
	 */
	private String resultMsg;
	
	private ResultCodeEnum(String resultCode,String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

}
