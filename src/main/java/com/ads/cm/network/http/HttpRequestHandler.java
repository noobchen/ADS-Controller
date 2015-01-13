package com.ads.cm.network.http;

import com.ads.cm.model.ClientRequestModel;
import com.ads.cm.network.protocol.CodecFactory;

import com.ads.cm.util.phone.PhoneUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import static org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Author: cyc
 * Date: 12-3-18
 * Time: 上午10:59
 * Description: 接收客户端请求之后处理分发请求内容
 */
public class HttpRequestHandler extends SimpleChannelUpstreamHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
    private HttpRequest request;
    private Map<String, CodecFactory> urlMaps;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        HttpRequest request = this.request = (HttpRequest) e.getMessage();

        if (is100ContinueExpected(request)) {
            send100Continue(e);
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());

        String url = queryStringDecoder.getPath();
        logger.debug("url:{}", url);
        CodecFactory codecFactory = urlMaps.get(url);


        if (null == codecFactory) {
            logger.error("unsupported url:{} request.", url);
            //sendError(ctx, BAD_REQUEST);
            e.getChannel().close();
            return;
        }

        //获取cmwap网络中的手机号码
        String phone = PhoneUtils.getPhone(request.getHeader("x-up-calling-line-id"));

        if (request.getMethod().equals(HttpMethod.POST)) {
            ChannelBuffer content = request.getContent();
            String postParams = content.toString(CharsetUtil.UTF_8);

            logger.debug("request POST content:{}", postParams);

            ClientRequestModel model = (ClientRequestModel) codecFactory.decode(postParams);
            model.setProperty(model.MESSAGE_EVENT_KEY, e);
            model.setProperty(model.HTTP_REQUEST_KEY, request);
            model.setProperty(model.HTTP_PHONE_KEY, phone);
            model.setProperty(model.REQUEST_CONTENT_KEY, postParams);

            InetSocketAddress remoteAddress = (InetSocketAddress) e.getRemoteAddress();
            model.setProperty(model.IP_KEY, remoteAddress.getAddress().getHostAddress());

            logger.info("client:{} user request model:{}", model.getModelIp(),model);

            model.fireSelf();

        } else if (request.getMethod().equals(HttpMethod.GET)) {
            Map<String, List<String>> requestParams = queryStringDecoder.getParameters();

            logger.debug("request content:{}", requestParams);

            ClientRequestModel model = (ClientRequestModel) codecFactory.decode(requestParams);
            model.setProperty(model.MESSAGE_EVENT_KEY, e);
            model.setProperty(model.HTTP_REQUEST_KEY, request);
            model.setProperty(model.HTTP_PHONE_KEY, phone);
            model.setProperty(model.REQUEST_CONTENT_KEY, requestParams);

            InetSocketAddress remoteAddress = (InetSocketAddress) e.getRemoteAddress();
            model.setProperty(model.IP_KEY, remoteAddress.getAddress().getHostAddress());

            logger.info("client;{} user request model:{}",model.getModelIp(), model);

            model.fireSelf();
        } else {
            logger.error("Isn't support http request:{} method:{}.", request, request.getMethod());
            //throw new RuntimeException("Is not supports http request method");
            e.getChannel().close();
        }
    }

    private void send100Continue(MessageEvent e) {
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, CONTINUE);
        e.getChannel().write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception {
        logger.warn("connection close.caught:{}", ExceptionUtils.getStackTrace(e.getCause()));
        e.getChannel().close();
    }

//    public static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
////        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
//
//        // Close the connection as soon as the error message is sent.
////        ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
//        ctx.getChannel().close();
//    }


    public void setUrlMaps(Map<String, CodecFactory> urlMaps) {
        this.urlMaps = urlMaps;
    }
}
