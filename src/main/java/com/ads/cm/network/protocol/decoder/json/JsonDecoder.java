package com.ads.cm.network.protocol.decoder.json;


import com.ads.cm.network.protocol.decoder.Decoder;
import com.ads.cm.util.encrypt.AESUtils;
import com.ads.cm.util.json.JsonUtils;

/**
 * Author: cyc
 * Date: 12-3-17
 * Time: 下午3:44
 * Description: to write something
 */
public class JsonDecoder implements Decoder<String> {
    private Class className;
    private boolean isEncrypt = true;

    @Override
    public Object decode(String o) throws Exception {
        if (isEncrypt) {
            return JsonUtils.jsonToObject(AESUtils.decode(o), className, true);
        } else {
            return JsonUtils.jsonToObject(o, className, true);
        }
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }
}
