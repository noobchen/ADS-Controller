package com.ads.cm.network.protocol.decoder.xml;


import com.ads.cm.constant.SystemConstants;
import com.ads.cm.network.protocol.decoder.Decoder;
import com.ads.cm.util.xml.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: cyc
 * Date: 12-4-27
 * Time: 下午2:00
 * Description: to write something
 */
public class XmlDecoder implements Decoder<String> {
    private final Logger logger = LoggerFactory.getLogger(XmlDecoder.class);
    private Class className;
    private String charset = SystemConstants.DEFAULT_ENCODE;

    @Override
    public Object decode(String o) throws Exception {
        logger.debug("xml:{} decode.", o);
        Object c = XmlUtils.xmlToObject(o, className, charset);
        logger.debug("xml decode object:{}.", c);
        return c;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
