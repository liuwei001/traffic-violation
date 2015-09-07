package com.traffic.weizhang.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.traffic.weizhang.entity.Supplier;
import com.traffic.weizhang.entity.Suppliers;
import com.traffic.weizhang.strategy.AbstractSuppplierStrate;
import com.traffic.weizhang.strategy.SuppplierContext;

/**
 * 供应商初始化加载
 * @author Administrator
 *
 */
public class SuppliersLoader {
	
	private static final Logger logger = Logger.getLogger(SuppliersLoader.class);
	
	private static Map<String,Map<String, String>> city_Supplier_Map = new HashMap<String, Map<String, String>>();
	
	private static Suppliers suppliers = new Suppliers();
	
	public static void init() {
		
		logger.info("初始化加载供应商配置.....start.");
		
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("supplier.xml");
		Digester digester = new Digester();
		digester.addObjectCreate("suppliers", Suppliers.class);
		digester.addObjectCreate("suppliers/supplier", Supplier.class);
		digester.addSetProperties("suppliers/supplier");
		digester.addSetNext("suppliers/supplier", "addSupplier");
		digester.addBeanPropertySetter("suppliers/supplier/name", "name");  
		digester.addBeanPropertySetter("suppliers/supplier/code", "code");  
		digester.addBeanPropertySetter("suppliers/supplier/app-id", "appId");  
		digester.addBeanPropertySetter("suppliers/supplier/app-key", "appKey");  
		digester.addBeanPropertySetter("suppliers/supplier/url", "url"); 
		digester.addBeanPropertySetter("suppliers/supplier/city-url", "cityUrl"); 
		digester.addBeanPropertySetter("suppliers/supplier/classname", "classname"); 
		
		try {
			suppliers = (Suppliers)digester.parse(inStream);
			List<Supplier> suppliersList = suppliers.getSupplierList();
			for(Supplier supplier : suppliersList) {
				String cityUrl = supplier.getCityUrl();
				
				AbstractSuppplierStrate instance;
				try {
					instance = (AbstractSuppplierStrate) Class.forName(supplier.getClassname()).newInstance();
					SuppplierContext context = new SuppplierContext(instance);
					
					//将城市列表转换成 key-value:  城市名称-拼音 形式
					Map<String, String> citiesMap = context.executeQueryCities(cityUrl);
					//存入内存
					city_Supplier_Map.put(supplier.getCode(), citiesMap);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					logger.error(ex.getMessage());
				} 
			}
			
			logger.info("初始化加载供应商配置....end.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Map<String, String>> getCity_Supplier_Map() {
		return city_Supplier_Map;
	}

	public static void setCity_Supplier_Map(Map<String, Map<String, String>> city_Supplier_Map) {
		SuppliersLoader.city_Supplier_Map = city_Supplier_Map;
	}

	public static void main(String[] args) {
		SuppliersLoader.init();
	}

	public static Suppliers getSuppliers() {
		return suppliers;
	}

	public static void setSuppliers(Suppliers suppliers) {
		SuppliersLoader.suppliers = suppliers;
	}

}
