package com.sy.notifyserver.util;



import com.sy.notifyserver.domain.BasicMessage;
import com.sy.notifyserver.domain.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信消息
 * @author wangxiao
 * @since 1.1
 */
public class WxMessageUtil {


    /**
     * @param @param  request
     * @param @return 微信XML 请求解析结果
     * @param @throws Exception
     * @Description: 解析微信发来的请求（XML数据包）, 并将结果封装在 Map 中返回
     */
    public static Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        Map<String, String> map = new HashMap<>(16);
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList){
            map.put(e.getName(), e.getText());
        }


        inputStream.close();
        inputStream = null;
        return map;
    }


    /**
     * 将消息对象转换成 xml 字符串
     * @param message 消息对象
     * @return xml 字符串
     */
    public static String parseObjMessageToXml(BasicMessage message) {
        XSTREAM.alias("xml", message.getClass());
        return XSTREAM.toXML(message);
    }


    /**
     * xstream框架本身并不支持CDATA块
     * 扩展xstream，使其支持微信消息XML数据包中的CDATA块
     */
    private static final XStream XSTREAM = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                boolean cdata = true;
                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }
                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

}
