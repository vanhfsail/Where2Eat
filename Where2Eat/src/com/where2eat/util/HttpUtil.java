package com.where2eat.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.where2eat.entiy.Reserve;
import com.where2eat.entiy.Restaurant;
import com.where2eat.entiy.User;

import android.util.Log;


public class HttpUtil {
	
	//public static final String BASE_URL="http://192.168.253.1:8080/Ngbussiness/";
	public static final String BASE_URL="http://192.168.253.1:8080/Ngbussiness/";
	//public static final String BASE_URL="http://172.31.34.188:8080/Ngbussiness/";
	
	public static HttpGet getHttpGet(String url){
		HttpGet request = new HttpGet(url);
		 return request;
	}
	
	public static HttpPost getHttpPost(String url){
		 HttpPost request = new HttpPost(url);
		 return request;
	}
	
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	
	public static String queryStringForPost(String url){
		HttpPost request = HttpUtil.getHttpPost(url);
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return null;
    }
	
	public static String queryStringForPost(HttpPost request){
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return null;
    }
	
	public static  String queryStringForGet(String url){
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return null;
    }
	
	 public static String getJsonContent(String url_path) {  
	        try {  
	            URL url = new URL(url_path);  
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
	            connection.setConnectTimeout(3000); // 请求超时时间3s  
	            connection.setRequestMethod("GET");  
	            connection.setDoInput(true);  
	            int code = connection.getResponseCode(); // 返回状态码  
	            if (code == 200) {  
	                // 或得到输入流，此时流里面已经包含了服务端返回回来的JSON数据了,此时需要将这个流转换成字符串  
	                return changeInputStream(connection.getInputStream());  
	            }  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	        }  
	        return "";  
	    }  
	  
	    private static String changeInputStream(InputStream inputStream) {  
	        // TODO Auto-generated method stub  
	        String jsonString = "";  
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  
	        int length = 0;  
	        byte[] data = new byte[1024];  
	        try {  
	            while (-1 != (length = inputStream.read(data))) {  
	                outputStream.write(data, 0, length);  
	            }  
	            // inputStream流里面拿到数据写到ByteArrayOutputStream里面,  
	            // 然后通过outputStream.toByteArray转换字节数组，再通过new String()构建一个新的字符串。  
	            jsonString = new String(outputStream.toByteArray());  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	        }  
	        return jsonString;  
	    }  
	    
	    public static List<User> jsonSelect(String url_path){
	    	
	    	String jsonString = getJsonContent(url_path);  
	    	
	    	 List<User> list2 = JSONTools.getUsers("user", jsonString) ;
        
          return list2; 
	    }
	    
public static List<Restaurant> jsonSelectRest(String url_path){
	    //public static String jsonSelectRest(String url_path){
	   
	
	    	String jsonString = getJsonContent(url_path);  
	    	
	    	 List<Restaurant> list2 = JSONTools.getRestaurant("restaurant", jsonString) ;
       // return jsonString;
          return list2; 
	    }

public static List<Reserve> jsonSelectOrder(String url_path){
	   // public static String jsonSelectOrder(String url_path){
	   
	
	    	String jsonString = getJsonContent(url_path);  
	    	
	    	 List<Reserve> list2 = JSONTools.getReserve("reserve", jsonString) ;
     
       return list2; 
	    }

public static List<Reserve> jsonSelectDetailOrder(String url_path){
	   // public static String jsonSelectOrder(String url_path){
	   
	
	    	String jsonString = getJsonContent(url_path);  
	    	
	    	 List<Reserve> list2 = JSONTools.getDetailReserve("reserve", jsonString) ;
  
    return list2; 
    }
	    
}
