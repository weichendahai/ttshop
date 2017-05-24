package com.baomidou.springwind.wx;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class HttpUtils {
	  private static Log log = LogFactory.getLog(HttpUtils.class);
	private static final String DEFAULT_HTTP_CHARSET = "UTF-8";
	private HttpUtils() {}
//httpGet
	public static String httpGet(String url) {
		String strReturn = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpUriRequest request = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			strReturn = EntityUtils.toString(entity, DEFAULT_HTTP_CHARSET);
		}catch(Exception e) {
			// 网络异常
			e.printStackTrace();
			log.error("网络异常"+e.getMessage());
		}finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("httpget无法关闭异常"+e);
			}
		}
		return strReturn;
	}
   //HttpPost
   public static String post(String url, String s)  {
	   CloseableHttpClient httpclient = HttpClients.createDefault();
	   HttpPost httpPost = new HttpPost(url);
          String result="";
	   httpPost.setEntity(new StringEntity(s, Charset.forName("UTF-8")));
	   try
	   {
		   // 执行post请求
		   CloseableHttpResponse httpResponse = httpclient.execute(httpPost);

		   int statusCode = httpResponse.getStatusLine().getStatusCode();
		   if (statusCode != HttpStatus.SC_OK)
		   {
			  log.error("post error.url->" + url + ",params->" + s + ",statuscode->" + statusCode);
			   return null;
		   }

		   HttpEntity entity = httpResponse.getEntity();
		    result = EntityUtils.toString(entity, Charset.forName("UTF-8"));
		   if (entity != null)
		   {
			   EntityUtils.consume(entity);
		   }
		   return result ;
	   } catch (IOException e)
	   {
		   e.printStackTrace();
		   log.error("io异常"+e);
	   } finally
	   {
		   try {
			   httpclient.close();
		   } catch (IOException e) {
			   e.printStackTrace();
			   log.error("io异常"+e);
		   }
	   }
	   return result ;
   }
}
