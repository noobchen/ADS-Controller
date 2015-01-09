package com.ads.cm.network.protocol.encoder.json;



import com.ads.cm.network.protocol.encoder.Encoder;
import com.ads.cm.util.json.JsonUtils;

import java.io.IOException;

/**
 * Author: cyc
 * Date: 12-3-17
 * Time: 下午9:37
 * Description: to write something
 */
public class JsonEncoder implements Encoder<Object> {

    @Override
    public String encode(Object o) throws IOException {
        return JsonUtils.objectToJson(o);
    }
}
