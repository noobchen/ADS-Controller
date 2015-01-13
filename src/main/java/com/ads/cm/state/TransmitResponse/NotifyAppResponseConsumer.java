package com.ads.cm.state.TransmitResponse;

import org.apache.http.HttpResponse;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Created by Administrator on 2015/1/12.
 */
public class NotifyAppResponseConsumer extends AsyncCharConsumer<String> {
    private String result;

    @Override
    protected void onCharReceived(CharBuffer charBuffer, IOControl ioControl) throws IOException {
        result = charBuffer.toString();
    }

    @Override
    protected void onResponseReceived(HttpResponse httpResponse) {

    }

    @Override
    protected String buildResult(HttpContext httpContext) throws Exception {
        return result;
    }
}
