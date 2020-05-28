package com.test.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.net.SocketTimeoutException;

/**
 * Created by jizhi.qian on 2019/3/13.
 */
public class HttpClient {
    private static int waitTime = 30; //30 秒超时

    public static String sendGet(String url, String access_token) {

        String charset = "utf-8";
        org.apache.http.client.HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;

        try {
            httpClient = new SSLClient(); //创建客户端client实例
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, waitTime * 1000);
            httpGet = new HttpGet(url);
            httpGet.setHeader("access_token", access_token); //配置Header

            HttpResponse response = httpClient.execute(httpGet); //执行get请求
            if (response != null) {
                HttpEntity resEntity = response.getEntity(); //获取响应实体
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset); //解析响应实体内容
                }
            }
        } catch (SocketTimeoutException ste) {
            result = "response timeout!";

        } catch (ConnectTimeoutException cte) {
            result = "response timeout!";

        } catch (Exception e) {
            e.printStackTrace();
            result = "Server internal error!";
        }

        return result;
    }

    public static String sendPost(String url, String data, String access_token) {
        String response = null;
        String contentType = "application/json";
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault(); //创建HttpClient对象
                HttpPost httppost = new HttpPost(url); //创建post请求对象

                //配置post请求
                StringEntity stringentity = new StringEntity(data,
                        ContentType.create(contentType, "UTF-8"));
                httppost.setEntity(stringentity);
                httppost.setHeader("access_token", access_token);

                //执行post请求
                httpresponse = httpclient.execute(httppost);

                //获取post请求响应实体
                response = EntityUtils
                        .toString(httpresponse.getEntity());
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String sendDelete(String url, String access_token) {
        String response = null;

        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault(); //创建Client对象
                HttpDelete httpdelete = new HttpDelete(url); //创建Delete请求
                httpdelete.setHeader("access_token", access_token);//配置delete请求
                httpresponse = httpclient.execute(httpdelete); //执行delete请求
                response = EntityUtils
                        .toString(httpresponse.getEntity()); //获取响应返回实体
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


}
