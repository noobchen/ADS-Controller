package com.ads.cm.state.report;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.GetTasksModel;
import com.ads.cm.model.ReportTaskStatusModel;
import com.ads.cm.repository.cache.Cache;
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
@Consumer("transmitReportTaskState")
public class TransmitReportTaskState implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(TransmitReportTaskState.class);
    private String requestUrl = "http://115.28.211.124:9008/report";
    private int timeout = 30;
    private Cache cache;

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        ReportTaskStatusModel reportTaskStatusModel = (ReportTaskStatusModel) event.getDomainMessage().getEventSource();

        String postContent = (String) reportTaskStatusModel.getProperty(reportTaskStatusModel.REQUEST_CONTENT_KEY);

        HttpAsyncClient httpClient = new DefaultHttpAsyncClient();
        httpClient.start();

        try {
            Future<String> future = httpClient.execute(HttpAsyncMethods.createPost(requestUrl, postContent, ContentType.APPLICATION_FORM_URLENCODED), new NotifyAppResponseConsumer(), null);
            String result = future.get(timeout, TimeUnit.SECONDS);

            HttpUtils.response(reportTaskStatusModel, result);
        } finally {
            httpClient.shutdown();
        }

    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

}
