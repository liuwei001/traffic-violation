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

public class CitiesUtil {
	
	private  Map<String, JSONArray> citiesMap = new HashMap<String, JSONArray>();

	/**
	 * 生成城市json
	 */
	public void genCityJson() {
		 	String fullFileName = this.getClass().getResource("/").getPath() + "cities.json";
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
							
							for(int i= 0;i < jarray.size(); i++) {
								JSONObject jobj = jarray.getJSONObject(i);
								String pinyin = jobj.getString("pinyin");
								jobj.remove("label");
								jobj.remove("zip");
								jobj.put("suppliers","1"); //供应商代号
								if(StringUtils.isNotBlank(pinyin)) {
									char start_char = pinyin.charAt(0) ;
									if(citiesMap.containsKey(String.valueOf(start_char))) {
										citiesMap.get(String.valueOf(start_char)).add(jobj);
									} else {
										JSONArray jobjArray = new JSONArray();
										jobjArray.add(jobj);
										citiesMap.put(String.valueOf(start_char), jobjArray);
									}
								}
							}
					}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			System.out.println(JSON.toJSONString(sortMapByKey(citiesMap)));
			
	}
	
	
	/** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, JSONArray> sortMapByKey(Map<String, JSONArray> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, JSONArray> sortMap = new TreeMap<String, JSONArray>(new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }  
	
	public static void main(String[] args) {
		new CitiesUtil().genCityJson();
	}
}


//比较器类  
class MapKeyComparator implements Comparator<String>{  
  public int compare(String str1, String str2) {  
      return str1.compareTo(str2);  
  }  
}  
