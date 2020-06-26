package com.sy.shope.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 快递工具
 * @author wangxiao
 * @since 1.1
 */
public final class ExpressUtil {

    private static final String host = "https://m.baidu.com/s?word=%E5%BF%AB%E9%80%92%E6%9F%A5%E8%AF%A2";
    private static final String searchUrl = "https://express.baidu.com/express/api/express?query_from_srcid=4155&isBaiduBoxApp=&tokenV2=TOKEN&appid=4001&nu=NU&com=_auto&qid=11931983723053215451&new_need_di=1&source_xcx=0&vcode=&token=&sourceId=4155";

    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(2000)
            .setSocketTimeout(10000).build();

    private static String cookieStr = null;

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    private final static String pattern = "(^|&)"+"tokenV2"+"=([^&]*)(&|$)";

    private final static Pattern compile = Pattern.compile(pattern);

    private static void setHtmlHeaders(HttpGet get) {
        get.setHeader("Accept-Encoding", "gzip, deflate");
        get.setHeader("Cache-Control", "max-age=0");
        get.setHeader("Connection", "keep-alive");
        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        get.setHeader("Host", "index.baidu.com");
        get.setHeader("Upgrade-Insecure-Requests", "1");
        get.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
    }


    private static void setXhrHeaders(HttpGet get) {
        get.setHeader("Host", "express.baidu.com");
        get.setHeader("Accept", "application/json");
        get.setHeader("Accept-Encoding", "gzip, deflate");
        get.setHeader("Cache-Control", "max-age=0");
        get.setHeader("Connection", "keep-alive");
        get.setHeader("Upgrade-Insecure-Requests", "1");
        get.setHeader("Cookie",cookieStr);
    }

    private static String getExpSearchApi () {
        String result = getHtml(host);
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByAttribute("data-repeatable").attr("type","application/json");
        for (Element element :elements) {
            if (element.html().contains("expSearchApi")) {
                JsonNode jsonNode = JsonUtil.parseJsonToJsonNode(element.html());
                return jsonNode.get("data").get("expSearchApi").asText();
            }
        }
        return null;
    }

    private static String getHtml(String url) {
        String result = null;
        try {
            HttpGet get = new HttpGet(url);
            setHtmlHeaders(get);
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse res = httpClient.execute(get,context);
            StringBuffer buffer = new StringBuffer();
            for (Cookie cookie : context.getCookieStore().getCookies()) {
                buffer.append(cookie.getName()).append("=").append(cookie.getValue());
            }
            cookieStr = buffer.deleteCharAt(buffer.length()-1).toString();
            result = EntityUtils.toString(res.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getBaiduExpressToken () {
        String url = getExpSearchApi();
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        Matcher matcher = compile.matcher(url);
        while (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    public static String searchExpressByNu (String token,String nu)  {
        String requestUrl = searchUrl;
        String url = requestUrl.replace("TOKEN",token).replace("NU",nu);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        setXhrHeaders(httpGet);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity,"unicode");
                JsonNode jsonNode = JsonUtil.parseJsonToJsonNode(result);
                if ("0".equals(jsonNode.get("status").asText())) {
                    return String.valueOf(jsonNode.get("data").get("info").get("context"));
                }else {
                    return jsonNode.get("msg").asText();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
