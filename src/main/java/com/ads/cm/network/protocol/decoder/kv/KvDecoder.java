package com.ads.cm.network.protocol.decoder.kv;



import com.ads.cm.network.protocol.decoder.Decoder;
import com.ads.cm.util.kv.KvUtils;

import java.util.List;
import java.util.Map;

/**
 * Author: cyc
 * Date: 12-4-6
 * Time: 下午2:09
 * Description: to write something
 */
public class KvDecoder implements Decoder<Map<String, List<String>>> {
    private Class className;

    @Override
    public Object decode(Map<String, List<String>> params) throws Exception {
        return KvUtils.instanceObject(params, className, true);
    }

    public void setClassName(Class className) {
        this.className = className;
    }
}
