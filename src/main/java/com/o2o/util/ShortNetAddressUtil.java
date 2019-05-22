package com.o2o.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShortNetAddressUtil {
    private static Logger logger = LoggerFactory.getLogger(ShortNetAddressUtil.class);

    public static int TIMEOUT = 30 * 1000;
    public static String ENCODING = "UTF-8";

    /**
     * 通过百度短视频将长链接转为短链接
     * 
     * @param originURL
     * @return
     */
    public static String getShortURL(String originURL) {
        String shortUrl = null;
        String params = "{\"url\":\"" + originURL + "\"}";
        try {
            // 指定百度短视频的接口
            URL url = new URL("https://dwz.cn/admin/v2/create");
            // 建立链接
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            // 设置链接的参数
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Token", "d08b4ce9e2d72168007a89b1024f25c7");
            connection.connect();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), ENCODING);
            outputStreamWriter.append(params);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            String responseStr = getResponseStr(connection);
            logger.info("response string:" + responseStr);
            shortUrl = getValueByKey(responseStr, "ShortUrl");
            logger.info("shortUrl:" + shortUrl);
            connection.disconnect();
        } catch (Exception e) {
            logger.error("getShortUrl error:" + e.toString());
        }
        return shortUrl;
    }

    /**
     * 获取返回流
     * 
     * @param connection
     * @return
     * @throws IOException
     */
    private static String getResponseStr(HttpURLConnection connection) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
            String inputLine = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
        }
        return String.valueOf(stringBuffer);
    }

    /**
     * 通过返回字符串获取需要的数据
     * 
     * @param responseStr
     * @param key
     * @return
     */
    private static String getValueByKey(String responseStr, String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node;
        String targetValue = null;
        try {
            node = objectMapper.readTree(responseStr);
            targetValue = node.get(key).asText();
        } catch (Exception e) {
            logger.error("getValueByKey error:" + e.toString());
        }
        return targetValue;
    }

    public static void main(String[] args) {
        getShortURL("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
    }
}
