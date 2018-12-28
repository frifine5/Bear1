package com.cy.common.http;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class HttpUtils {

    public static String httpSendAndReceive(List<NameValuePair> reqList, String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(reqList, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            HttpResponse response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String rtn = "";
            if (entity != null) {
                String jsonBody = EntityUtils.toString(entity, "UTF-8");
                if (!jsonBody.trim().startsWith("{")) {
                    rtn = jsonBody;
                } else {
                    JSONObject json = new JSONObject(rtn);
                    rtn = json.toString();
                }
                Log.i("bus", "Response content: " + rtn);
            }
            return rtn;
        } catch (JSONException e) {
            Log.e("bus", "JSON转换异常:返回结果不是JSON格式", e);
        } catch (ClientProtocolException e) {
            Log.e("bus", "httpclient 请求异常：客户端协议", e);
        } catch (UnsupportedEncodingException e) {
            Log.e("bus", "httpclient 请求异常：字符集编码", e);
        } catch (IOException e) {
            Log.e("bus", "httpclient 请求异常：I/O异常", e);
        }
        return null;
    }


}
