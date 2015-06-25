package com.traffic.common.holder;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 读取cfg.properties属性文件配置支持类
 * @author Administrator
 *
 */
public final class PropertiesCfgHolder {
	
	private static ResourceBundle rb;
	
	static {
		rb = ResourceBundle.getBundle("cfg", Locale.getDefault());
	}
	
	public static String getProperty(String key) {
		return rb.getString(key);
	}

}
