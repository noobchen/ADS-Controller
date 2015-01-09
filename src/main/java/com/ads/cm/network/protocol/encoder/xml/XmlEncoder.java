package com.ads.cm.network.protocol.encoder.xml;


import com.ads.cm.constant.SystemConstants;
import com.ads.cm.network.protocol.encoder.Encoder;
import com.ads.cm.util.xml.XmlUtils;

/**
 * Author: Tony.Wang
 * Date: 12-4-27
 * Time: 下午1:59
 * Description: to write something
 */
public class XmlEncoder implements Encoder<Object> {
    private String charset = SystemConstants.DEFAULT_ENCODE;

    @Override
    public String encode(Object o) throws Exception {
        return XmlUtils.objectToXml(o, charset);
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
