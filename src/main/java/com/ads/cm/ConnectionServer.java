package com.ads.cm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: cyc
 * Date: 12-3-17
 * Time: 下午10:19
 * Description: to write something
 */
public class ConnectionServer {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionServer.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-all.xml");

        context.registerShutdownHook();

        logger.info("Connection server start successful...");
    }
}
