package com.ads.cm.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import com.ads.cm.util.annotation.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: cyc
 * Date: 12-4-27
 * Time: 下午2:02
 * Description: to write something
 */
public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    public static String objectToXml(Object o, String charset) {
        XStream xstream = new XStream(new DomDriver(charset));

        String root = AnnotationUtils.getXml(o.getClass());

        xstream.alias(root, o.getClass());

        return xstream.toXML(o);
    }

    public static <T> T xmlToObject(String xml, Class<T> className, String charset) throws IllegalAccessException, InstantiationException {
        Object r = className.newInstance();

        XStream xstream = new XStream(new DomDriver(charset));

        String root = AnnotationUtils.getXml(className);

        xstream.alias(root, className);

        Object o = xstream.fromXML(xml, r);

        return (T) o;
    }


}
