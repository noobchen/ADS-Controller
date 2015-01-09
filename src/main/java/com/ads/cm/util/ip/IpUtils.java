package com.ads.cm.util.ip;

/**
 * Author: cyc
 * Date: 12-3-21
 * Time: 上午10:14
 * Description: to write something
 */
public class IpUtils {
    /**
     * IP地址转换成整形
     *
     * @param ip
     * @return
     */
    public static long stringIpToLong(String ip) {
        long result = 0;
        java.util.StringTokenizer token = new java.util.StringTokenizer(ip, ".");
        result += Long.parseLong(token.nextToken()) << 24;
        result += Long.parseLong(token.nextToken()) << 16;
        result += Long.parseLong(token.nextToken()) << 8;
        result += Long.parseLong(token.nextToken());
        return result;
    }

    /**
     * 整形转换成IP地址
     *
     * @param ipLong
     * @return
     */
    public static String longIpToString(long ipLong) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipLong >>> 24);
        sb.append(".");
        sb.append(String.valueOf((ipLong & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ipLong & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(ipLong & 0x000000FF));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(IpUtils.stringIpToLong("1.25.25.0"));
    }
}
