package com.ads.cm.util.phone;

import com.ads.cm.constant.ProviderConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Author: cyc
 * Date: 12-3-21
 * Time: 上午10:10
 * Description: to write something
 */
public class PhoneUtils {
    public static String getPhone(String phone) {
        if (StringUtils.isEmpty(phone) || phone.length() < 11) {
            return null;
        } else if (phone.length() > 11) {
            return phone.substring(phone.length() - 11);
        } else {
            return phone;
        }
    }

    /**
     * 截取合规规范（IMEI长度为15数字，IMSI总长度不超过15数字）的IMEI或者IMSI
     * @param str
     * @return
     */
    public static String getImeiOrImsi(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else if (str.length() > 15) {                      //大于15只截取0--15位
            return str.substring(0, 14);
        } else {                                            //小于15直接返回
            return str;
        }
    }

    /**
     * 根据IMSI判断出运营商
     *
     * @param imsi
     * @return
     */
    public static int getProviderId(String imsi) {
        if (StringUtils.isNotEmpty(imsi)) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
                return ProviderConstants.CHINA_MOBILE;
            } else if (imsi.startsWith("46001")) {
                return ProviderConstants.CHINA_UNICOM;
            } else if (imsi.startsWith("46003")) {
                return ProviderConstants.CHINA_TELECOM;
            } else {
                //默认是中国移动
                return ProviderConstants.CHINA_MOBILE;
            }
        } else {
            //默认是中国移动
            return ProviderConstants.CHINA_MOBILE;
        }
    }
}
