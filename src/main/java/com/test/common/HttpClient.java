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
            httpClient = new SSLClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, waitTime * 1000);
            httpGet = new HttpGet(url);
            httpGet.setHeader("access_token", access_token);

            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
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
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(data,
                        ContentType.create(contentType, "UTF-8"));
                httppost.setEntity(stringentity);
                httppost.setHeader("access_token", access_token);
                httpresponse = httpclient.execute(httppost);
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
                httpclient = HttpClients.createDefault();
                HttpDelete httpdelete = new HttpDelete(url);
                httpdelete.setHeader("access_token", access_token);
                httpresponse = httpclient.execute(httpdelete);
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


}
