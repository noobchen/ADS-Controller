package com.ads.cm.network.protocol;

import com.ads.cm.network.protocol.decoder.Decoder;
import com.ads.cm.network.protocol.encoder.Encoder;

/**
 * Author: cyc
 * Date: 12-3-17
 * Time: 下午3:00
 * Description: to write something
 */
public class JsonCodecFactory implements CodecFactory {
    private Decoder decoder;
    private Encoder encoder;

    public JsonCodecFactory(Decoder decoder, Encoder encoder) {
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override
    public Object decode(Object o) throws Exception {
        return decoder.decode(o);
    }

    @Override
    public Object encode(Object o) throws Exception {
        return encoder.encode(o);
    }
}
