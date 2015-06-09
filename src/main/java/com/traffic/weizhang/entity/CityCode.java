package com.traffic.weizhang.entity;


/**
 * 供应商对应的citycode
 * @author Administrator
 *
 */
public class CityCode {

	private String citycode;
	
	//供应商指定city code
	private String targetCode;
	
	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

}
