package com.traffic.common.message;


/**
 * http请求响应对象统一格式，响应时将会被转换成json格式返回
 * @Description:TODO
 * @Copyright Copyright 2014-2015 traffic Tech. Co. Ltd. All Rights Reserved.<BR>
 * @author： liuwei <BR>
 * @Time： 2015年2月3日 <BR>
 * @version 1.0.0 <BR>
 */
public class ResponseMessage {

	/**
	 * 返回编码
	 */
	private String resultCode = "0"; 
	
	/**
	 * 返回信息
	 */
	private String resultMsg = "操作成功";
	
	/**
	 * 响应对象
	 */
	private Object result;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	
	
}
