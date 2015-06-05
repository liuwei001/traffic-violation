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
	private int returnCode = 0; 
	
	/**
	 * 返回信息
	 */
	private String returnInfo = "操作成功";
	
	/**
	 * 响应对象
	 */
	private Object response;

	
	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	
	
}
