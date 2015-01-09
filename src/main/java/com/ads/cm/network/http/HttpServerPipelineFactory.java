package com.ads.cm.network.http;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import static org.jboss.netty.channel.Channels.pipeline;

/**
 * Author: cyc
 * Date: 12-3-12
 * Time: 上午11:11
 * Description: to write something
 */
public class HttpServerPipelineFactory implements ChannelPipelineFactory {
    private ChannelUpstreamHandler channelUpstreamHandler;

    public ChannelPipeline getPipeline() throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline pipeline = pipeline();

        // Uncomment the following line if you want HTTPS
        //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
        //engine.setUseClientMode(false);
        //pipeline.addLast("ssl", new SslHandler(engine));

        pipeline.addLast("decoder", new HttpRequestDecoder());
        // Uncomment the following line if you don't want to handle HttpChunks.
        pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        // Remove the following line if you don't want automatic content compression.
        pipeline.addLast("deflater", new HttpContentCompressor());
        //pipeline.addLast("handler", new HttpRequestHandler());
        pipeline.addLast("handler", channelUpstreamHandler);
        return pipeline;
    }

    public void setChannelUpstreamHandler(ChannelUpstreamHandler channelUpstreamHandler) {
        this.channelUpstreamHandler = channelUpstreamHandler;
    }
}

