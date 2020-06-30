package com.zhengbing.cloud.starter.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * http client 发送请求
 *
 * @author zhengbing_vendor
 * @date 2020/6/29
 **/
public class HttpClient {

    private static final String HTTP = "http";

    private static Logger log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * 根据请求路径生成请求连接，并初始化连接的基础设置
     *
     * @param url 请求路径
     * @return URLConnection
     * @throws IOException IOException 生成连接的产生的异常
     */
    private static URLConnection getUrlConnection(String url) throws IOException {
        URL sendUrl = new URL(url);
        URLConnection connection = sendUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return connection;
    }

    /**
     * 无参数发起GET请求
     *
     * @param url String 请求路径
     * @return String 响应结果
     */
    public static String sendGet(String url) {
        return sendGet(url, null);
    }

    /**
     * 无参数发起POST请求
     *
     * @param url String 请求路径
     * @return String 响应结果
     */
    public static String sendPost(String url) {
        return sendPost(url, null);
    }

    /**
     * 发送Get请求
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return String 请求响应结果（Json处理过）
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlName = url;
        if (!url.startsWith(HTTP)) {
            log.error("请求路径不正确，缺少【 http 】 schema");
            return null;
        }
        if (StringUtils.isNotEmpty(param)) {
            urlName += "?" + param;
        }
        log.info("开始发送请求至：{}", urlName);
        try {
            URLConnection connection = getUrlConnection(urlName);
            connection.connect();
            //获取所有的响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            //遍历所有的响应头字段
            log.info("请求响应header");
            for (String key : map.keySet()) {
                log.info(key + " --> " + map.get(key));
            }
            // 读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("请求响应结果{}", result);
            if (StringUtils.isNotEmpty(result)) {
                return result.toString();
            }
        } catch (IOException e) {
            log.error("发送get请求 {}，出现异常{}", urlName, GsonUtils.toJsonString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * 发送POST请求
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return String json处理之后的结果
     */
    public static String sendPost(String url, String param) {

        StringBuilder result = new StringBuilder();

        log.info("开始发送请求至：{}", url);
        try {
            URLConnection connection = getUrlConnection(url);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            PrintWriter pw = new PrintWriter(connection.getOutputStream());
            pw.print(param);
            pw.flush();
            // 读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("请求响应结果：{}", result);
            if (StringUtils.isNotEmpty(result)) {
                return result.toString();
            }
        } catch (IOException e) {
            log.error("发送post请求 {}，出现异常{}", url, GsonUtils.toJsonString(e.getStackTrace()));
        }
        return null;
    }

    public static void main(String[] args) {
        HttpClient.sendGet("https://newoa.sensetime.com/senseapi/v1/ehr/getUnoperators?requestid=888792");
    }
}
