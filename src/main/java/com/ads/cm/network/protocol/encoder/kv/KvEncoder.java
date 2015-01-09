package com.ads.cm.network.protocol.encoder.kv;



import com.ads.cm.network.protocol.encoder.Encoder;
import com.ads.cm.util.kv.KvUtils;

import java.util.List;
import java.util.Map;

/**
 * Author: cyc
 * Date: 12-4-6
 * Time: 下午2:08
 * Description: to write something
 */
public class KvEncoder implements Encoder<Map<String, List<String>>> {
    @Override
    public Object encode(Map<String, List<String>> o) throws Exception {
        return KvUtils.objectToString(o);
    }
}
