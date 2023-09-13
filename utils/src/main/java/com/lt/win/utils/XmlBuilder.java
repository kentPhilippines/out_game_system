package com.lt.win.utils;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

/**
 * Xml转Bean
 */
public class XmlBuilder {

    public static <T> T toBean(Class<T> clazz, String xml) {
        Object xmlObject;
        XStream xstream = new XStream(new Xpp3Driver(new NoNameCoder()));
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(clazz);
        xstream.autodetectAnnotations(true);
        xmlObject = xstream.fromXML(xml);

        return JSON.parseObject(JSON.toJSONString(xmlObject)).toJavaObject(clazz);
    }
}
