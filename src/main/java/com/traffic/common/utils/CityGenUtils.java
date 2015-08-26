/* 
 * Copyright (c) 2015, QUANRONG E-COMMDERCE LTD. All rights reserved.
 */
package com.traffic.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015年8月26日      vmp         Create
 * ****************************************************************************
 * </pre>
 * @author vmp
 * @since 1.0
 */
public class CityGenUtils {
	private static Map<String, JSONArray> citysMap = new HashMap<String, JSONArray>();
	
	public void test() {
		if(citysMap == null || citysMap.keySet().size() == 0 ) {
			 	String fullFileName = this.getClass().getResource("/").getPath() + "city.json";
		        File file = new File(fullFileName);
		        Scanner scanner = null;
		        StringBuilder buffer = new StringBuilder();
		        try {
		            scanner = new Scanner(file, "utf-8");
		            while (scanner.hasNextLine()) {
		                buffer.append(scanner.nextLine());
		            }
		 
		        } catch (FileNotFoundException e) {
		 
		        } finally {
		            if (scanner != null) {
		                scanner.close();
		            }
		        }
		         
			String respBody = buffer.toString();
			try {
				if(StringUtils.isNotBlank(respBody)) {
						JSONArray jarray = JSONArray.parseArray(respBody);
						if(jarray != null && jarray.size() > 0) {
							
							for(int i = 0 ;i < jarray.size(); i++) {
								JSONObject jobj = jarray.getJSONObject(i);
								String pinyin = jobj.getString("pinyin");
								char start_char = pinyin.charAt(0);
								jobj.remove("zip");
								jobj.remove("label");
								if(citysMap.containsKey(String.valueOf(start_char))) {
									citysMap.get(String.valueOf(start_char)).add(jobj);
								} else {
									JSONArray array = new JSONArray();
									array.add(jobj);
									citysMap.put(String.valueOf(start_char), array);
								}
							}
						}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			citysMap = sortMapByKey(citysMap);
		} 
		
	}
	
	
	 /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public Map<String, JSONArray> sortMapByKey(Map<String, JSONArray> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
 
        Map<String, JSONArray> sortMap = new TreeMap<String, JSONArray>(
                new MapKeyComparator());
 
        sortMap.putAll(map);
 
        return sortMap;
    }
	
	public static void main(String[] args) {
		new CityGenUtils().test();
		
		System.out.println(JSON.toJSONString(citysMap));
	}
}

class MapKeyComparator implements Comparator<String>{
	 
    @Override
    public int compare(String str1, String str2) {
         
        return str1.compareTo(str2);
    }
}
