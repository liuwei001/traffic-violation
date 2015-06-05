package com.traffic.common.configuration;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * cfg.properties属性配置文件读取类
 * @Description:TODO
 * @Copyright Copyright 2014-2015 traffic Tech. Co. Ltd. All Rights Reserved.<BR>
 * @author： liuwei <BR>
 * @Time： 2015年2月4日 <BR>
 * @version 1.0.0 <BR>
 */
public class PropertiesCfgHolder {

	private static ResourceBundle rb;
	
	static {
		rb = ResourceBundle.getBundle("cfg", Locale.getDefault());
	}
	
	/**
	 * 根据key获取配置的属性值
	 *getProperty  
	 *@param key
	 *@return
	 */
	public static String getProperty(String key) {
		return rb.getString(key);
	}
}
