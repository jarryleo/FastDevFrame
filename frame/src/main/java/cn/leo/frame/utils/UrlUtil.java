package cn.leo.frame.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Leo on 2017/5/16.
 */

public class UrlUtil {
    /**
     * 以map形式获取url的参数列表
     *
     * @param url
     * @return
     */
    public static LinkedHashMap<String, String> getUrlParamsMap(String url) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        String[] splitBp = url.split("\\?", 2); //切分基础url和参数
        //String baseUrl = splitBp[0]; //拿到基础地址
        if (splitBp.length > 1) {
            String paramStr = splitBp[1];
            String[] singleParam = paramStr.split("&");//获取每个参数的键值对
            for (String s : singleParam) {
                String[] param = s.split("=");//把键值对拆开获取键和值
                String key = param[0]; //参数键
                String value = param[1];//参数值
                params.put(key, value);
            }
        }
        return params;
    }

    /**
     * 拿到连接中除了参数的地址
     *
     * @param url
     * @return
     */
    public static String getBaseUrl(String url) {
        String[] splitBp = url.split("\\?", 2); //切分基础url和参数
        String baseUrl = splitBp[0]; //拿到基础地址
        return baseUrl;
    }

    /**
     * 把基础地址和参数组合成url地址
     *
     * @param baseUrl
     * @param params
     * @return
     */
    public static String getUrlString(String baseUrl, Map<String, String> params) {
        StringBuilder urlSb = new StringBuilder(baseUrl);
        urlSb.append("?");//添加基础地址和参数分割符
        Set<Map.Entry<String, String>> paramSet = params.entrySet();
        for (Map.Entry<String, String> entry : paramSet) {//遍历键值对
            urlSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        urlSb.deleteCharAt(urlSb.length() - 1);
        return urlSb.toString();
    }

    /**
     * 往url插入map参数
     *
     * @param url
     * @param params
     * @return
     */
    public static String addParamsToUrl(String url, Map<String, String> params) {
        String baseUrl = getBaseUrl(url);
        LinkedHashMap<String, String> urlParamsMap = getUrlParamsMap(url);
        urlParamsMap.putAll(params);
        return getUrlString(baseUrl, urlParamsMap);
    }

    /**
     * 往url插入一个参数
     *
     * @param url
     * @param key
     * @param value
     * @return
     */
    public static String addParamToUrl(String url, String key, String value) {
        String baseUrl = getBaseUrl(url);
        LinkedHashMap<String, String> urlParamsMap = getUrlParamsMap(url);
        urlParamsMap.put(key, value);
        return getUrlString(baseUrl, urlParamsMap);
    }
    //中文编码转化
    //URLEncoder.encode("张三","UTF-8");
    //URLDecoder.decode("%E6%B5%8B","UTF-8");
}
