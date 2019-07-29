package com.fhz.microservicecommonapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author fenghouzhi
 * @date 2019-07-26 - 09:16
 * @description: http请求工具类
 */
@Slf4j
public class HttpRequestUtil {


    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * http请求工具类，post请求
     *
     * @param url    url
     * @param params 参数值 仅支持String和list两种类型
     */
    public static String httpPost(String url, Map<String, Object> params) throws Exception {
        CloseableHttpClient httpClient = null;
        BufferedReader bufferedReader = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            if (params != null) {
                String jsonParams = objectMapper.writeValueAsString(params);
                log.info("url:{},参数值：{}", url, jsonParams);
                HttpEntity httpEntity = new StringEntity(jsonParams, "utf-8");
                httpPost.setEntity(httpEntity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                String errorLog = "请求失败，errorCode:" + httpResponse.getStatusLine().getStatusCode();
                log.info(errorLog);
                throw new Exception(url + errorLog);
            }
            String output;
            bufferedReader = new BufferedReader(
                    new InputStreamReader(httpResponse.getEntity().getContent(), "utf-8"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }
            return stringBuilder.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    /**
     * http请求工具类，get请求
     */
    public static String httpGet(String url, Map<String, Object> params, String... resonseCharSet)
            throws Exception {
        CloseableHttpClient httpClient = null;
        BufferedReader bufferedReader = null;
        try {
            httpClient = HttpClients.createDefault();
            if (params != null) {
                StringBuilder stringBuilder = new StringBuilder();
                Iterator<String> iterator = params.keySet().iterator();
                String key;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    Object val = params.get(key);
                    if (val instanceof List) {
                        List v = (List) val;
                        for (Object o : v) {
                            stringBuilder.append(key).append("=").append(o.toString()).append("&");
                        }
                    } else {
                        stringBuilder.append(key).append("=").append(val.toString()).append("&");
                    }
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                url = url + "?" + stringBuilder.toString();
                log.info("url:{}", url);
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json;charset=ut-8");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                String errorLog = "请求失败，errorCode:" + httpResponse.getStatusLine().getStatusCode();
                log.info(errorLog);
                throw new Exception(url + errorLog);
            }
            String charSet = "utf-8";
            if (resonseCharSet != null && resonseCharSet.length > 0) {
                charSet = resonseCharSet[0];
            }
            String output;
            bufferedReader = new BufferedReader(
                    new InputStreamReader(httpResponse.getEntity().getContent(), charSet));

            StringBuilder dataBuilder = new StringBuilder();
            while ((output = bufferedReader.readLine()) != null) {
                dataBuilder.append(output);
            }
            return dataBuilder.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    /**
     * http请求工具类，post请求获取二进制数据
     *
     * @param url    url
     * @param params 参数值 仅支持 String 和 List 两种类型
     */
    public static Map<String, Object> httpPostCode(String url, Map<String, Object> params, File file,
                                                   boolean flag) throws Exception {
        CloseableHttpClient httpClient = null;
        BufferedReader bufferedReader = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "*/*");
            if (params != null) {
                String jsonParams = objectMapper.writeValueAsString(params);
                log.info("url:{},参数值：{}", url, jsonParams);
                HttpEntity httpEntity = new StringEntity(jsonParams, "utf-8");
                httpPost.setEntity(httpEntity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                String errorLog = "请求失败，errorCode:" + httpResponse.getStatusLine().getStatusCode();
                log.info(errorLog);
                throw new Exception(url + errorLog);
            }

            Map<String, Object> mapResult = new HashMap<String, Object>(2);
            mapResult.put("isSuccess", true);
            if (flag) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(bos);
                ByteArrayInputStream swapInputStream = new ByteArrayInputStream(bos.toByteArray());
                mapResult.put("inputStream", swapInputStream);
            } else {
                log.info("二进制流:{},获取seuccess=====   ", httpResponse.getEntity().getContent());
                InputStream input = httpResponse.getEntity().getContent();
                FileOutputStream output = new FileOutputStream(file);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = input.read(bytes)) != -1) {
                    output.write(bytes, 0, read);
                }
                output.flush();
                output.close();
            }

            return mapResult;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

}