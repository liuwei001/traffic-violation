package com.traffic.weizhang.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 省份
 * @author Administrator
 *
 */
public class Province implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String province_code;
	
	private String province;
	
	private List<City> citys;

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<City> getCitys() {
		return citys;
	}

	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	
}
