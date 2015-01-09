package com.ads.cm.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 14-10-30.
 */
public class LogInstance {
    public static final Logger registerLogger = LoggerFactory.getLogger("register");
    public static final Logger getTaskLogger = LoggerFactory.getLogger("getask");
    public static final Logger reportTaskLogger = LoggerFactory.getLogger("reportask");
    public static final Logger updateTaskLogger = LoggerFactory.getLogger("updatetask");
    public static final Logger loadManagerLogger = LoggerFactory.getLogger("loadmanager");

}
