package com.ads.cm.state.register;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.RegisterModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.state.TransmitResponse.NotifyAppResponseConsumer;
import com.ads.cm.util.encrypt.AESUtils;
import com.ads.cm.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/1/12.
 */
@Consumer("transmitRegisterState")
public class TransmitRegisterState implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(TransmitRegisterState.class);
    private String requestUrl = "http://115.28.211.124:9008/register";
    private int timeout = 30;
    private Cache cache;

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        RegisterModel registerModel = (RegisterModel) event.getDomainMessage().getEventSource();
        String postContent = (String) registerModel.getProperty(registerModel.REQUEST_CONTENT_KEY);
        logger.debug("On TransmitRegisterState postContent:{}", postContent);
        HttpAsyncClient httpClient = new DefaultHttpAsyncClient();
        httpClient.start();

        try {
            Future<String> future = httpClient.execute(HttpAsyncMethods.createPost(requestUrl, postContent, ContentType.APPLICATION_FORM_URLENCODED), new NotifyAppResponseConsumer(), null);
            String result = future.get(timeout, TimeUnit.SECONDS);
            result = AESUtils.decode(result);

            if (StringUtils.isNotEmpty(result)) {
                logger.debug("TransmitRegister response:{}", result);

                RegisterResponse registerResponse = JsonUtils.jsonToObject(result, RegisterResponse.class, false);

                if (registerResponse.getResultCode().equals("200")) {
                    long indexId = registerResponse.getIndexId();

                    String zlbRegisterKey = CacheConstants.ZLB_REGISTER_ID_KEY_ + registerModel.getImei() + CacheConstants.CACHE_KEY_SEPARATOR + registerModel.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + registerModel.getChannelName();
                    cache.set(zlbRegisterKey, indexId + "");
                    cache.setTimeout(zlbRegisterKey, 60 * 24 * 60 * 60);
                }
            }
        } finally {
            httpClient.shutdown();
        }

    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    private static class RegisterResponse {

        private RegisterResponse() {
        }

        private String resultCode;
        private Long indexId;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public Long getIndexId() {
            return indexId;
        }

        public void setIndexId(Long indexId) {
            this.indexId = indexId;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("resultCode", resultCode)
                    .append("indexId", indexId)
                    .toString();
        }
    }
}
