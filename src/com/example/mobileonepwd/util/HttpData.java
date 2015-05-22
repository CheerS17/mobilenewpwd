package com.example.mobileonepwd.util;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leaf
 * Date: 14-4-12
 * Time: 上午1:54
 * To change this template use File | Settings | File Templates.
 */
public class HttpData {
    private int code;
    private String result;
    private HttpEntity entity;

    public int getCode() {
        return code;
    }

    private HttpEntity httpClientPost(String httpUrl, String key[],
                                      String value[]) {
        HttpClient httpClient = new DefaultHttpClient();

        HttpParams httpParams = httpClient.getParams();
        int REQUEST_TIMEOUT = 5 * 1000;  //设置请求超时10秒钟
        int SO_TIMEOUT = 5 * 1000;       //设置等待数据超时时间10秒钟

        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

        HttpPost post = new HttpPost(httpUrl);
        HttpEntity entity;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (int i = 0; i < key.length; i++) {
            params.add(new BasicNameValuePair(key[i], value[i]));
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = httpClient.execute(post);
            code = response.getStatusLine().getStatusCode();
            entity = response.getEntity();

            return entity;

        } catch (Exception e) {
            // TODO: handle exception        	
//            Log.e("HttpData", e.getMessage());
            Log.e("HttpData", httpUrl);
            e.printStackTrace();
            return null;
        }
    }

////    private HttpEntity binaryClientPost(String httpUrl,
////                                        ArrayList<Map<String, String>> keyValuePair,
////                                        byte[] buffer, String bufferName) {
////        HttpClient httpClient = new DefaultHttpClient();
////        HttpPost post = new HttpPost(httpUrl);
////        HttpEntity entity;
//////        MultipartEntity mulEntity = new MultipartEntity();
////        MultipartEntity mulEntity = new MultipartEntity(
////                HttpMultipartMode.BROWSER_COMPATIBLE,
////                "###########samboundray#############",
////                Charset.forName("utf-8"));
////        try {
////            for (int i = 0; i < keyValuePair.size(); i++) {
////                StringBody value = new StringBody(
////                        keyValuePair.get(i).get("value"),
////                        Charset.forName("UTF-8"));
////                mulEntity.addPart(keyValuePair.get(i).get("key"), value);
////            }
////            mulEntity.addPart(bufferName, new ByteArrayBody(buffer, bufferName));
////            post.setEntity(mulEntity);
////            HttpResponse response = httpClient.execute(post);
////            code = response.getStatusLine().getStatusCode();
////            entity = response.getEntity();
////            return entity;
////        } catch (Exception e) {
////            // TODO: handle exception
////            Log.e("HttpData", e.getMessage());
////            Log.e("HttpData", httpUrl);
////            e.printStackTrace();
////            return null;
////        }
////    }
//
//    private HttpEntity fileClientPost(String httpUrl,
//                                      ArrayList<Map<String, String>> keyValuePair,
//                                      ArrayList<Map<String, String>> files) {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost post = new HttpPost(httpUrl);
//        HttpEntity entity;
//        //       MultipartEntity mulEntity = new MultipartEntity();
//        MultipartEntity mulEntity = new MultipartEntity(
//                HttpMultipartMode.BROWSER_COMPATIBLE,
//                "###########samboundray#############",
//                Charset.forName("utf-8"));
//        try {
//            for (int i = 0; i < keyValuePair.size(); i++) {
//                StringBody value = new StringBody(
//                        keyValuePair.get(i).get("value"),
//                        Charset.forName("UTF-8"));
//                mulEntity.addPart(keyValuePair.get(i).get("key"), value);
//            }
//            for (int i = 0; i < files.size(); i++) {
//                File file = new File(files.get(i).get("file"));
//                FileBody httpFile = new FileBody(file);
//                mulEntity.addPart(files.get(i).get("fileKey"), httpFile);
//            }
//            post.setEntity(mulEntity);
//            HttpResponse response = httpClient.execute(post);
//            code = response.getStatusLine().getStatusCode();
//            entity = response.getEntity();
//            return entity;
//        } catch (Exception e) {
//            // TODO: handle exception
//            Log.e("HttpData", e.getMessage());
//            Log.e("HttpData", httpUrl);
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    public HttpEntity getEntity(String httpUrl, String key[],
                                String value[]) {
        return httpClientPost(httpUrl, key, value);
    }

    public String getContent(String httpUrl, String key[],
                             String value[]) {
        HttpEntity entity = httpClientPost(httpUrl, key, value);
        if (entity == null)
            return null;
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;
        try {
            is = entity.getContent();
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            result = br.readLine();
        } catch (IOException e) {
            Log.e("HttpData", e.getMessage());
            Log.e("HttpData", "getContent()    url:" + httpUrl);
            e.printStackTrace();            
        }
        return result;        
    }

//    public String getBinaryPostContent(String httpUrl,
//                                       ArrayList<Map<String, String>> keyValuePair,
//                                       byte[] buffer,
//                                       String bufferName) {
//        HttpEntity entity = binaryClientPost(httpUrl, keyValuePair, buffer, bufferName);
//        if (entity == null)
//            return null;
//        InputStream is;
//        InputStreamReader isr;
//        BufferedReader br;
//        try {
//            is = entity.getContent();
//            isr = new InputStreamReader(is, "utf-8");
//            br = new BufferedReader(isr);
//            result = br.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public String getFilePostContent(String httpUrl,
//                                     ArrayList<Map<String, String>> keyValuePair,
//                                     ArrayList<Map<String, String>> files) {
//        HttpEntity entity = fileClientPost(httpUrl, keyValuePair, files);
//        if (entity == null)
//            return null;
//        InputStream is;
//        InputStreamReader isr;
//        BufferedReader br;
//        try {
//            is = entity.getContent();
//            isr = new InputStreamReader(is, "utf-8");
//            br = new BufferedReader(isr);
//            result = br.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("HttpData", e.getMessage());
//            Log.e("HttpData", "getFilePostContent()    url:" + httpUrl);
//        }
//        return result;
//    }
}
