package com.ads.cm.constant;

import org.jboss.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Author: cyc
 * Date: 12-3-18
 * Time: 下午3:21
 * Description: to write something
 */
public class SystemConstants {
    public static final String DEFAULT_ENCODE = "UTF-8";
    public static final Charset DEFAULT_CHARSET = CharsetUtil.UTF_8;
    public static final String DEFAULT_HTTP_CONTENT_TYPE = "application/json;charset=UTF-8";


    public static final int ABILITY_PUSH = 1;
    public static final int ABILITY_BANNER = 2;
    public static final int ABILITY_ICON = 3;


//    public static final int STATE_ON = 1;
    public static final int STATE_OFF = 0;
    public static final int STATE_SHOWN_OUT = 1;              //本应用外显示
    public static final int STATE_SHOWN_IN = 2;               //本应用内显示
    public static final int STATE_SHOWN_EV = 3;               //任意位置显示


    public static final int COUNT_BY_SHOW = 1;
    public static final int COUNT_BY_CLICK = 2;
    public static final int SHOW_FOREVER = 3;


    public static final String LOGIN = "1";
    public static final String ALREADYLOGIN = "2";
    public static final String UNLOGIN = "0";




}

