package com.ads.cm.network.http;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Author: cyc
 * Date: 12-3-12
 * Time: 上午10:31
 * Description: to write something
 */
public class HttpServer {
    private final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private final int DEFAULT_PORT = 80;
    private int port = DEFAULT_PORT;
    private ChannelPipelineFactory pipelineFactory;

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the event pipeline factory.
        //bootstrap.setPipelineFactory(new HttpServerPipelineFactory());
        bootstrap.setPipelineFactory(pipelineFactory);

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(port));
        logger.info("Http server bind port:{} successful...", port);
    }


    public void setPort(int port) {
        this.port = port;
    }

    public void setPipelineFactory(ChannelPipelineFactory pipelineFactory) {
        this.pipelineFactory = pipelineFactory;
    }
}
