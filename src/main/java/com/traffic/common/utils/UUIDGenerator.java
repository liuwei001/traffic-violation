package com.traffic.common.utils;

import java.util.UUID;

/**
 * @ClassName: UUIDGenerator
 * @Title:
 * @Description:TODO(取得UUID)
 * @Author:Administrator
 * @Since:2012-2-15上午11:02:03
 * @Version:1.0
 */
public class UUIDGenerator {
	public UUIDGenerator() {
	}

	/**
	 * @Title: getUUID
	 * @Description: TODO(取得一个UUID)
	 * @Author: Administrator
	 * @Since: 2012-2-15上午11:00:48
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.replace("-", "");
	}

	/**
	 * 获得指定数目的UUID
	 * 
	 * @param number
	 *            int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	public static void main(String[] args) {
		String[] ss = getUUID(10);
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i] + "    " + ss[i].length());
		}
	}
}