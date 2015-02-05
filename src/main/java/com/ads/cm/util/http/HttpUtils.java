package com.ads.cm.util.http;

import com.ads.cm.constant.SystemConstants;
import com.ads.cm.model.*;
import com.ads.cm.util.encrypt.AESUtils;
import com.ads.cm.util.json.JsonUtils;
import com.ads.cm.util.log.LogInstance;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Author: cyc
 * Date: 12-3-20
 * Time: 上午12:11
 * Description: to write something
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static boolean encrypt = true;

    public static void response(ClientRequestModel model, Object jsonObject) {
        String responseString = JsonUtils.objectToJson(jsonObject);
        try {
            if (encrypt) {
                response(model, AESUtils.encode(responseString));
                logger.debug("client;{} response:{} successful.request:{}", new Object[]{model.getModelIp(), responseString, model});
                classifySuccessLog(model, responseString);
            } else {
                response(model, responseString);
            }
        } catch (Exception e) {
            logger.error("client:{} response:{} failure.exception:{}", new Object[]{model.getModelIp(), responseString, ExceptionUtils.getStackTrace(e)});
             classifyFailLog(model,responseString,e);
        }
    }

    public static void response(ClientRequestModel model, String content) {
        // Decide whether to close the connection or not.
        HttpRequest request = (HttpRequest) model.getProperty(model.HTTP_REQUEST_KEY);
        boolean keepAlive = isKeepAlive(request);

        // Build the response object.
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);

        response.setContent(ChannelBuffers.copiedBuffer(content, SystemConstants.DEFAULT_CHARSET));
        response.setHeader(CONTENT_TYPE, SystemConstants.DEFAULT_HTTP_CONTENT_TYPE);

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
        }

        // Write the response.
        MessageEvent e = (MessageEvent) model.getProperty(model.MESSAGE_EVENT_KEY);
        ChannelFuture future = e.getChannel().write(response);

        // Close the non-keep-alive connection after the write operation is done.
        if (!keepAlive) {

            future.addListener(ChannelFutureListener.CLOSE);
        }

        logger.debug("client:{} response:{} successful.request:{}", new Object[]{model.getModelIp(), content, request});
    }

    public void setEncrypt(boolean encrypt) {
        HttpUtils.encrypt = encrypt;
    }

    public static void classifySuccessLog(ClientRequestModel model, String content) {
        if (model instanceof GetTasksModel) {                  //  GetTasksModel 是     RegisterModel 的子类，避免重复打印
            LogInstance.getTaskLogger.debug("client:{} response:{} successful.request:{}", new Object[]{model.getModelIp(), content, model});
            return;
        }

        if (model instanceof RegisterModel) {
            LogInstance.registerLogger.debug("client:{} response:{} successful.request:{}", new Object[]{model.getModelIp(), content, model});
        }



        if (model instanceof ReportTaskStatusModel) {
            LogInstance.reportTaskLogger.debug("client:{} response:{} successful.request:{}", new Object[]{model.getModelIp(), content, model});
        }

        if (model instanceof UpdateTaskStatusModel) {
            LogInstance.updateTaskLogger.debug("client:{} response:{} successful.request:{}", new Object[]{model.getModelIp(), content, model});
        }

        if (model instanceof LoadManagerModel) {
            LogInstance.loadManagerLogger.debug("client:{} response:{} successful.request:{}", new Object[]{model.getModelIp(), content, model});
        }


    }

    public static void classifyFailLog(ClientRequestModel model, String responseString,Exception e) {
        if (model instanceof GetTasksModel) {                  //  GetTasksModel 是     RegisterModel 的子类，避免重复打印
            LogInstance.getTaskLogger.error("client:{} response:{} failure.exception:{}", new Object[]{model.getModelIp(), responseString, ExceptionUtils.getStackTrace(e)});
            return;
        }

        if (model instanceof RegisterModel) {
            LogInstance.registerLogger.error("client:{} response:{} failure.exception:{}", new Object[]{model.getModelIp(), responseString, ExceptionUtils.getStackTrace(e)});
        }



        if (model instanceof ReportTaskStatusModel) {
            LogInstance.reportTaskLogger.error("client:{} response:{} failure.exception:{}", new Object[]{model.getModelIp(), responseString, ExceptionUtils.getStackTrace(e)});
        }

        if (model instanceof UpdateTaskStatusModel) {
            LogInstance.updateTaskLogger.error("client:{} response:{} failure.exception:{}", new Object[]{model.getModelIp(), responseString, ExceptionUtils.getStackTrace(e)});
        }

        if (model instanceof LoadManagerModel) {
            LogInstance.loadManagerLogger.error("client:{} response:{} failure.exception:{}", new Object[]{model.getModelIp(), responseString, ExceptionUtils.getStackTrace(e)});
        }


    }
}
