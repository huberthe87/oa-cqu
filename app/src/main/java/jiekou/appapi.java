package jiekou;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import android.util.Log;
import android.widget.Toast;

public class appapi {
	
	
    static String BOUNDARY = java.util.UUID.randomUUID().toString();  
    static String PREFIX = "--", LINEND = "\r\n";  
    static String MULTIPART_FROM_DATA = "multipart/form-data";  
    static String CHARSET = "UTF-8";

	public static String getWebServiceRes(Map<String,Object> params,URL url) {  
        try {  
        	//访问准  		
        	Log.d(">>>>>>>>>", "1");
    		 //开始访问
    		  StringBuilder postData = new StringBuilder();
    		  for (Map.Entry<String,Object> param : params.entrySet()) {
    		      if (postData.length() != 0) postData.append('&');
    		      postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
    		      postData.append('=');
    		      postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
    		  }
    		  Log.d(">>>>>>>>>", "2");
    		  byte[] postDataBytes = postData.toString().getBytes("UTF-8");
    		  Log.d(">>>>>>>>>", "3");
    		  HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    		  Log.d(">>>>>>>>>", "4");
    		  conn.setRequestMethod("POST");
    		  conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    		  conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
    		  conn.setDoOutput(true);
    		  conn.getOutputStream().write(postDataBytes);
    		  Log.d(">>>>>>>>>", "5");
    		  
    		  int code = conn.getResponseCode();
              Log.d("wahahahaha", ""+code);
    		  Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    		  Log.d(">>>>>>>>>", "6");
    		  StringBuilder sb = new StringBuilder();
    		  for (int c; (c = in.read()) >= 0;)
    		      sb.append((char)c);
    		  String response = sb.toString(); 
              
    		  System.out.print(">>>>>>>"+response);
              return response;  
              
        } catch (Exception e) {  
            e.printStackTrace();  
            return "失败";  
       }  
        
        
        
    } 
	
	
	public static String getWebServiceRestupianwenben(URL url, Map<String,Object> paramMap,Map<String,Object>imgMap  )  
            throws Exception{  
  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
  
        conn.setDoInput(true);// 允许输入  
        conn.setDoOutput(true);// 允许输出  
        conn.setUseCaches(false);  
        conn.setReadTimeout(10 * 1000); // 缓存的最长时间  
        conn.setRequestMethod("POST");  
  
        conn.setRequestProperty("Charset", "UTF-8");  
        conn.setRequestProperty("Connection", "Keep-Alive");  
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);  
  
  
        DataOutputStream os =  new DataOutputStream(conn.getOutputStream());  
  
        StringBuilder sb = new StringBuilder(); //用StringBuilder拼接报文，用于上传图片数据  
        sb.append(PREFIX);  
        sb.append(BOUNDARY);  
        sb.append(LINEND);  
        sb.append("Content-Disposition: form-data; name=\""+imgMap.get("1")+"\"; filename=\"" + ((File)imgMap.get("2")).getName() + "\"" + LINEND);  
        sb.append("Content-Type: image/jpg; charset=" + CHARSET + LINEND);  
        sb.append(LINEND);  
        os.write(sb.toString().getBytes());  
        InputStream is = new FileInputStream((File)imgMap.get("2"));  
  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while ((len = is.read(buffer)) != -1) {  
            os.write(buffer, 0, len); //写入图片数据  
        }  
        is.close();  
        os.write(LINEND.getBytes());  
  
        StringBuilder text = new StringBuilder();  
        for(Map.Entry<String,Object> entry : paramMap.entrySet()) { //在for循环中拼接报文，上传文本数据  
            text.append("--");  
            text.append(BOUNDARY);  
            text.append("\r\n");  
            text.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");  
            text.append(entry.getValue());  
            text.append("\r\n");  
        }  
        os.write(text.toString().getBytes("UTF-8")); //写入文本数据  
  
        // 请求结束标志  
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();  
        os.write(end_data);  
        os.flush();  
        os.close();  
  
//        // 得到响应码  
//        int res = conn.getResponseCode();  
//        System.out.println("asdf code "+ res);  
//        System.out.println("asdf " + conn.getResponseMessage());  
//        conn.disconnect(); 
        
          Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		  StringBuilder sb2 = new StringBuilder();
		  for (int c; (c = in.read()) >= 0;)
		      sb2.append((char)c);
		  String response = sb2.toString(); 
 
		  System.out.print(">>>>>>>"+response);
        return response; 
        
//        return conn.getResponseMessage();
    }  
	
	
	
	
	
	
	public static String getWebServiceRestupianwenben2222(URL url, Map<String,Object> paramMap,Map<String,Object>imgMap  )  
            throws Exception{  
  
		  HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
		  
	        conn.setDoInput(true);// 允许输入  
	        conn.setDoOutput(true);// 允许输出  
	        conn.setUseCaches(false);  
	        conn.setReadTimeout(10 * 1000); // 缓存的最长时间  
	        conn.setRequestMethod("POST");  
	  
	        conn.setRequestProperty("Charset", "UTF-8");  
	        conn.setRequestProperty("Connection", "Keep-Alive");  
	        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);  
	  
	  
	        DataOutputStream os =  new DataOutputStream(conn.getOutputStream());  
	  
	        StringBuilder sb = new StringBuilder(); //用StringBuilder拼接报文，用于上传图片数据  
	        sb.append(PREFIX);  
	        sb.append(BOUNDARY);  
	        sb.append(LINEND);  
	        sb.append("Content-Disposition: form-data; name=\""+imgMap.get("1")+"\"; filename=\"" + ((File)imgMap.get("2")).getName() + "\"" + LINEND);  
	        sb.append("Content-Type: image/jpg; charset=" + CHARSET + LINEND);  
	        sb.append(LINEND);  
	        os.write(sb.toString().getBytes());  
	        InputStream is = new FileInputStream((File)imgMap.get("2"));  
	  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while ((len = is.read(buffer)) != -1) {  
	            os.write(buffer, 0, len); //写入图片数据  
	        }  
	        is.close();  
	        os.write(LINEND.getBytes());  
	  
	        StringBuilder text = new StringBuilder();  
	        for (Map.Entry<String,Object> param : paramMap.entrySet()) {
			      if (text.length() != 0) text.append('&');
			      text.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			      text.append('=');
			      text.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			  }	        
	        os.write(text.toString().getBytes("UTF-8")); //写入文本数据  
	  
	        // 请求结束标志  
	        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();  
	        os.write(end_data);  
	        os.flush();  
	        os.close();  
	  
//	        // 得到响应码  
  	        int res = conn.getResponseCode();  
  	        
//  	        Toast.makeText(d(), res, Toast.LENGTH_LONG).show();   
//	        System.out.println("asdf code "+ res);  
//	        System.out.println("asdf " + conn.getResponseMessage());  
//	        conn.disconnect(); 
	        
	          Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			  StringBuilder sb2 = new StringBuilder();
			  for (int c; (c = in.read()) >= 0;)
			      sb2.append((char)c);
			  String response = sb2.toString(); 
	 
			  System.out.print(">>>>>>>"+response);
	        return response; 
	        
//	        return conn.getResponseMessage();
	    }  
	
	

	
}
