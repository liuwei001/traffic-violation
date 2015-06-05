/* 
 * Copyright (c) 2015, QUANRONG E-COMMDERCE LTD. All rights reserved.
 */
package com.traffic.common.utils.http;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * 描述：HttpClient连接池
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015年5月13日      vmp         Create
 * ****************************************************************************
 * </pre>
 * @author vmp
 * @since 1.0
 */
public class HttpConnectionManager {
 
   private static HttpParams httpParams;  
   
   private static PoolingClientConnectionManager pccm ;
 
   /** 
    * 最大连接数 
    */  
   public final static int MAX_TOTAL_CONNECTIONS = 800;  
   /** 
    * 获取连接的最大等待时间 
    */  
   public final static int WAIT_TIMEOUT = 60000;  
   /** 
    * 每个路由最大连接数 
    */  
   public final static int MAX_ROUTE_CONNECTIONS = 400;  
   
 
   static {  
	// 设置组件参数, HTTP协议的版本,1.1/1.0/0.9   
	    HttpParams params = new BasicHttpParams();   
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);   
	    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");   
	    HttpProtocolParams.setUseExpectContinue(params, true);      
	  
	    //设置连接超时时间   
	    int REQUEST_TIMEOUT = 10*1000;  //设置请求超时10秒钟   
	    
	    int SO_TIMEOUT = 10*1000;       //设置等待数据超时时间10秒钟   
	    
	    //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);  
	    //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);  
	    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);    
	    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);   
	    
	    //设置访问协议   
	    SchemeRegistry schreg = new SchemeRegistry();    
	    schreg.register(new Scheme("http",80,PlainSocketFactory.getSocketFactory()));   
	    schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));         
	      
	    //多连接的线程安全的管理器   
	    pccm = new PoolingClientConnectionManager(schreg);  
	    pccm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS); //每个主机的最大并行链接数   
	    pccm.setMaxTotal(MAX_TOTAL_CONNECTIONS);          //客户端总并行链接最大数  
   }  
 
   public static HttpClient getHttpClient() {  
       return new DefaultHttpClient(pccm, httpParams);
   }  

}
