package com.ads.cm.state.getTask;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.GetTasksModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.state.TransmitResponse.GetTaskResponse;
import com.ads.cm.state.TransmitResponse.GetTaskStatusResponse;
import com.ads.cm.state.TransmitResponse.NotifyAppResponseConsumer;
import com.ads.cm.util.encrypt.AESUtils;
import com.ads.cm.util.http.HttpUtils;
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
@Consumer("transmitGetTaskState")
public class TransmitGetTaskState implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(TransmitGetTaskState.class);
    private String requestUrl = "http://115.28.211.124:9008/getTasks";
    private int timeout = 30;
    private Cache cache;

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        GetTasksModel getTasksModel = (GetTasksModel) event.getDomainMessage().getEventSource();

        String zlbRegisterKey = CacheConstants.ZLB_REGISTER_ID_KEY_ + getTasksModel.getImei() + CacheConstants.CACHE_KEY_SEPARATOR + getTasksModel.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + getTasksModel.getChannelName();

        String phoneIndex = (String) cache.get(zlbRegisterKey);

        String postContent = (String) getTasksModel.getProperty(getTasksModel.REQUEST_CONTENT_KEY);

        String deCodeContent = AESUtils.decode(postContent);

        logger.debug("On TransmitGetTaskState postContent:{}", postContent);

        GetTasksModel object = JsonUtils.jsonToObject(deCodeContent, getTasksModel.getClass(), false);

        String phoneIndexOri = object.getPhoneIndex();

        deCodeContent = deCodeContent.replace(phoneIndexOri, phoneIndex);

        String encodePostContent = AESUtils.encode(deCodeContent);

        logger.debug("On TransmitGetTaskState encodePostContent:{}", encodePostContent);

        HttpAsyncClient httpClient = new DefaultHttpAsyncClient();
        httpClient.start();

        try {
            Future<String> future = httpClient.execute(HttpAsyncMethods.createPost(requestUrl, encodePostContent, ContentType.APPLICATION_FORM_URLENCODED), new NotifyAppResponseConsumer(), null);
            String result = future.get(timeout, TimeUnit.SECONDS);

            String decodeResult = AESUtils.decode(result);
            logger.debug("On TransmitGetTaskState response result:{}", decodeResult);

            if (StringUtils.isNotEmpty(decodeResult)) {
                GetTaskStatusResponse getTaskStatusResponse = JsonUtils.jsonToObject(decodeResult, GetTaskStatusResponse.class, false);

                if (getTaskStatusResponse.getResultCode().equals("200")) {
                    HttpUtils.response(getTasksModel, result);

                    GetTaskResponse getTaskResponse =getTaskStatusResponse.getErrorCode().get(0);

                    int reportTblId = getTaskResponse.getReportTblId();

                    String zlbTaskKey = CacheConstants.ZLB_GETTASK_KEY + reportTblId;

                    cache.set(zlbTaskKey, reportTblId + "");
                    cache.setTimeout(zlbTaskKey, 24 * 60 * 60);
                } else {
                    getTasksModel.fireSelf();
                }

            }
        } finally {
            httpClient.shutdown();
        }

    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }




}
