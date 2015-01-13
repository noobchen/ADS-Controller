package com.ads.cm.state.report;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.UpdateTaskStatusModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.getTask.GetTasksDao;
import com.ads.cm.state.TransmitResponse.NotifyAppResponseConsumer;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午3:38
 * 插屏结果更新
 */
@Consumer("transmitUpdateInstallStatus")
public class TransmitUpdateInstallStatus implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(TransmitUpdateInstallStatus.class);
    private String requestUrl = "http://115.28.211.124:9008/update";
    private int timeout = 30;
    private Cache cache;

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        UpdateTaskStatusModel updateTaskStatusModel = (UpdateTaskStatusModel) event.getDomainMessage().getEventSource();

        String postContent = (String) updateTaskStatusModel.getProperty(updateTaskStatusModel.REQUEST_CONTENT_KEY);

        HttpAsyncClient httpClient = new DefaultHttpAsyncClient();
        httpClient.start();

        try {
            Future<String> future = httpClient.execute(HttpAsyncMethods.createPost(requestUrl, postContent, ContentType.APPLICATION_FORM_URLENCODED), new NotifyAppResponseConsumer(), null);
            String result = future.get(timeout, TimeUnit.SECONDS);

            HttpUtils.response(updateTaskStatusModel, result);
        } finally {
            httpClient.shutdown();
        }

    }


    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
