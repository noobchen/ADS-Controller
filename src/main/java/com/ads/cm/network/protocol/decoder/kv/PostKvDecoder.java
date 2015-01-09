package com.ads.cm.network.protocol.decoder.kv;


import com.ads.cm.network.protocol.decoder.Decoder;
import com.ads.cm.util.kv.KvUtils;
import org.jboss.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wy
 * Date: 12-7-11
 * Time: 下午9:08
 * To change this template use File | Settings | File Templates.
 */
public class PostKvDecoder implements Decoder<String> {
    private Charset charset = CharsetUtil.UTF_8;
    private Class className;

    @Override
    public Object decode(String params) throws Exception {
        params = params.replaceAll("&amp;", "yctest");
        Map<String, List<String>> requestParms = decodeParams(params);
        return KvUtils.instanceObject(requestParms, className, true);
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    private Map<String, List<String>> decodeParams(String s) {
        Map<String, List<String>> params = new LinkedHashMap<String, List<String>>();
        String name = null;
        int pos = 0;
        int i;
        char c = 0;
        for (i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c == '=' && name == null) {
                if (pos != i) {
                    name = decodeComponent(s.substring(pos, i), charset);
                }
                pos = i + 1;
            } else if (c == '&') {
                if (name == null && pos != i) {
                    addParam(params, decodeComponent(s.substring(pos, i), charset), "");
                } else if (name != null) {
                    addParam(params, name, decodeComponent(s.substring(pos, i), charset));
                    name = null;
                }
                pos = i + 1;
            }
        }

        if (pos != i) {
            if (name == null) {
                addParam(params, decodeComponent(s.substring(pos, i), charset), "");
            } else {
                addParam(params, name, decodeComponent(s.substring(pos, i), charset));
            }
        } else if (name != null) {
            addParam(params, name, "");
        }

        return params;
    }

    private String decodeComponent(String s, Charset charset) {
        if (s == null) {
            return "";
        }

        try {
            return URLDecoder.decode(s, charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedCharsetException(charset.name());
        }
    }

    private void addParam(Map<String, List<String>> params, String name, String value) {
        List<String> values = params.get(name);
        if (values == null) {
            values = new ArrayList<String>(1);
            params.put(name, values);
        }
        values.add(value);
    }
}
