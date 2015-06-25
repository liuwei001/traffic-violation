package weizhang;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015年6月25日      vmp         Create
 * ****************************************************************************
 * </pre>
 * @author vmp
 * @since 1.0
 */
public class SimpleServiceTest {
	/**
	 * http接口调用
	 * @param url
	 * @param busiName
	 * @return
	 */
	public String httpPost(String url,Map<String, String> headers,String json){
		String res = null;
		HttpURLConnection myurlcon = null;
		try {
			URL myurl = new URL(null, url,new sun.net.www.protocol.http.Handler());
			myurlcon = (HttpURLConnection) myurl.openConnection();
			myurlcon.setDoOutput(true);
			myurlcon.setDoInput(true);
			myurlcon.setDefaultUseCaches(false);
			myurlcon.setUseCaches(false);
			myurlcon.setRequestMethod("POST");
			myurlcon.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			myurlcon.setConnectTimeout(20000);
			myurlcon.setReadTimeout(20000);
			
			if (headers != null) {
				for (String key : headers.keySet()) {
					myurlcon.setRequestProperty(key, headers.get(key));
				}
			}
			
			myurlcon.connect();
			if(json != null){
				DataOutputStream out = new DataOutputStream(myurlcon
		                .getOutputStream());
//				json = URLEncoder.encode(json,"UTF-8");
				System.out.println(json);
				// 发送请求参数
				out.writeBytes(json);
				// flush输出流的缓冲
				out.flush();
				out.close();
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					myurlcon.getInputStream(), "utf-8"));
			StringBuffer bf = new StringBuffer("");
			String strread = null;
			while ((strread = in.readLine()) != null) {
				bf.append(strread);
				strread = null;
			}
			in.close();
			myurlcon.disconnect();
			JSONObject array = JSONObject.parseObject(bf.toString());
			
			
			
			System.out.println("接口返回："+array);
		} catch (Exception ex) {
			System.out.println("接口返回："+ex);
		} finally {
			if (myurlcon != null) {
				myurlcon.disconnect();
				myurlcon = null;
			}
		}
		return res;
	}
//2758255392
	public static void main(String[] args) {
		//将cst时间格式转换为GMT时间格式
		DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = format.format(new Date());
//		Map<String, String> map = new HashMap<>();
//		map.put("license_plate_num", "粤BA804D");
//		map.put("engine_num", "C32764");
//		map.put("body_num", "064484");
//		map.put("city_pinyin", "shenzhen");
//		JSONObject jsonObject = JSONObject.fromObject(map);
		String s = "license_plate_num=%E7%B2%A4BA804D&engine_num=C32764&body_num=064484&city_pinyin=shenzhen";
		//计算签名
		String SIGNATURE = md5("POST&/v3/violations&"+date+"&"+s.length()+"&"+ md5("uTIYrJn6vJTyt1ztBNbqQQDexDjpAM4m"));
		System.out.println(SIGNATURE);
		//设置请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Host", "api.buding.cn");
		headers.put("Authorization", "dLSQ1ZjK7exqlwqx:" + SIGNATURE);
		headers.put("Date", date);
		SimpleServiceTest simpleServiceTest = new SimpleServiceTest();
		simpleServiceTest.httpPost("http://api.buding.cn/v3/violations", headers, s);
	}
		
	
	
	private static String md5(String msg) {
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			instance.update(msg.getBytes("UTF-8"));
			byte[] md = instance.digest();
			return byteArrayToHex(md);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static String byteArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (byte b : a) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

}
