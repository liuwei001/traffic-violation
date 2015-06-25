package com.traffic.weizhang.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.traffic.weizhang.entity.CityCode;
import com.traffic.weizhang.entity.Supplier;
import com.traffic.weizhang.entity.Suppliers;

/**
 * 供应商初始化加载
 * @author Administrator
 *
 */
public class SuppliersLoader {
	
	private static final Logger logger = Logger.getLogger(SuppliersLoader.class);
	
	private static Map<String,String[]> city_Supplier_Map = new HashMap<String, String[]>();

	public static void init() {
		
		logger.info("初始化加载供应商配置.....start.");
		
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("supplier.xml");
		Digester digester = new Digester();
		digester.addObjectCreate("suppliers", Suppliers.class);
		digester.addObjectCreate("suppliers/supplier", Supplier.class);
		digester.addSetProperties("suppliers/supplier");
		digester.addSetNext("suppliers/supplier", "addSupplier");
		
		digester.addBeanPropertySetter("suppliers/supplier/description", "description");  
		digester.addBeanPropertySetter("suppliers/supplier/classname", "classname"); 
		digester.addBeanPropertySetter("suppliers/supplier/url", "url"); 
		
		digester.addObjectCreate("suppliers/supplier/cityCodes/city_code", CityCode.class);
		digester.addSetProperties("suppliers/supplier/cityCodes/city_code"); 
		digester.addBeanPropertySetter("suppliers/supplier/cityCodes/city_code", "citycode"); 
		digester.addBeanPropertySetter("suppliers/supplier/cityCodes/city_code/targetCode", "targetCode"); 
		digester.addSetNext("suppliers/supplier/cityCodes/city_code", "addCityCode");
		
		
		try {
			Suppliers suppliers = (Suppliers)digester.parse(inStream);
			List<Supplier> suppliersList = suppliers.getSupplierList();
			for(Supplier supplier : suppliersList) {
				List<CityCode> cityCodeList = supplier.getCityCodeList();
				if(cityCodeList != null && cityCodeList.size() > 0) {
					for(CityCode cityCode : cityCodeList) {
						city_Supplier_Map.put(cityCode.getCitycode(), new String[]{cityCode.getTargetCode() == null?cityCode.getCitycode():cityCode.getTargetCode(),supplier.getUrl(),supplier.getClassname()});
					}
				} else { //默认供应商
					city_Supplier_Map.put("default",new String[]{"", supplier.getUrl(),supplier.getClassname()});
				}
			}
			
			logger.info("初始化加载供应商配置....end.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String[]> getCity_Supplier_Map() {
		return city_Supplier_Map;
	}

	public static void setCity_Supplier_Map(Map<String, String[]> city_Supplier_Map) {
		SuppliersLoader.city_Supplier_Map = city_Supplier_Map;
	}

	public static void main(String[] args) {
		SuppliersLoader.init();
	}
}
