package cn.com.caogen.weixin.util;

import cn.com.caogen.weixin.entity.TextMessage;
import com.thoughtworks.xstream.XStream;
import jdk.internal.util.xml.SAXParser;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.sax.SAXResult;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/9
 */
public class MessageUtil {
    public static Map<String,String>  XmlToMap(HttpServletRequest request){
        Map<String,String> map=new HashMap<String,String>();
        try {
        InputStream inputStream= request.getInputStream();
            SAXReader reader=new SAXReader();
            Document documen=reader.read(inputStream);
            Element root=documen.getRootElement();
            List<Element> elementList=root.elements();
            for(Element element:elementList){
                map.put(element.getName(),element.getText());
            }
            inputStream.close();

        }catch (Exception e){

        }
        return map;
    }

    public static String textMessageXml(TextMessage textMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }
}
