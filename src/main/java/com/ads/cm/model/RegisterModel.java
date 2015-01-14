package com.ads.cm.model;

import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.util.event.EventUtils;
import com.ads.cm.util.phone.PhoneUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 上午11:59
 * 20141028 add channelName 渠道名称  delete service_center 短信中心号
 */
public class RegisterModel extends ClientRequestModel {

    public Long id;

    public String isRoot;

    public String sdk_version;

    public String app_key;

    public String phoneNum;

    public String imei;

    public String imsi;

    public String phone_factory;   //手机厂商

    public String phone_version;  //手机型号

//    public String service_center;  //短信中心号

    public String os_version;  //操作系统版本

    public String packageName;

    public String app_Name;

    public int os_int;

    public String mac;

    public String channelName = "undefined"; //app渠道名,默认值是undefined兼容之前版本。

    @JsonIgnore
    public Long appId ;

    @JsonIgnore
    private String ip;

    @JsonIgnore
    public Area area;



    /**
     * `imei` varchar(12) DEFAULT NULL COMMENT '手机串号',
     * `phone_num` varchar(12) DEFAULT NULL COMMENT '手机号码',
     * `phone_factory` varchar(10) DEFAULT NULL COMMENT '手机厂商',
     * `phone_version` varchar(15) DEFAULT NULL COMMENT '手机型号',
     * `os_version` varchar(8) DEFAULT NULL COMMENT 'android系统版本',
     * `os_int` tinyint(4) DEFAULT NULL COMMENT 'android系统版本整数值',
     * `root_state` tinyint(4) DEFAULT NULL COMMENT 'root状态，1已root，0未root',
     * `mac` varchar(50) DEFAULT NULL COMMENT 'mac地址',
     */

    public int getOs_int() {
        return os_int;
    }

    public void setOs_int(int os_int) {
        this.os_int = os_int;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        if (StringUtils.isNotEmpty(mac) && mac.length() > 50) {
            this.mac = mac.substring(0, 49);
        } else {
            this.mac = mac;
        }

    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoot() {
        return isRoot;
    }

    public void setRoot(String root) {
        isRoot = root;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = PhoneUtils.getImeiOrImsi(imei);
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = PhoneUtils.getImeiOrImsi(imsi);
    }

    public String getPhone_factory() {
        return phone_factory;
    }

    public void setPhone_factory(String phone_factory) {

        if (StringUtils.isNotEmpty(phone_factory) && phone_factory.length() > 10) {
            this.phone_factory = phone_factory.substring(0, 9);
        } else {
            this.phone_factory = phone_factory;
        }
    }

    public String getPhone_version() {
        return phone_version;
    }

    public void setPhone_version(String phone_version) {

        if (StringUtils.isNotEmpty(phone_version) && phone_version.length() > 15) {
            this.phone_version = phone_version.substring(0, 14);
        } else {
            this.phone_version = phone_version;
        }

    }

//    public String getService_center() {
//        return service_center;
//    }
//
//    public void setService_center(String service_center) {
//        this.service_center = service_center;
//    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        if (StringUtils.isNotEmpty(os_version) && os_version.length() > 8) {
            this.os_version = os_version.substring(0, 7);
        } else {
            this.os_version = os_version;
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApp_Name() {
        return app_Name;
    }

    public void setApp_Name(String app_Name) {
        this.app_Name = app_Name;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        if (StringUtils.isNotEmpty(channelName)) {
            this.channelName = channelName;
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("isRoot", isRoot)
                .append("sdk_version", sdk_version)
                .append("app_key", app_key)
                .append("phoneNum", phoneNum)
                .append("imei", imei)
                .append("imsi", imsi)
                .append("phone_factory", phone_factory)
                .append("phone_version", phone_version)
                .append("os_version", os_version)
                .append("packageName", packageName)
                .append("app_Name", app_Name)
                .append("os_int", os_int)
                .append("mac", mac)
                .append("channelName", channelName)
                .append("appId", appId)
                .append("ip", ip)
                .append("area", area)
                .toString();
    }

    @Override
    public DomainMessage fireSelf() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "registerState");
        return em;

    }

    public DomainMessage getAppInfos() {

        DomainMessage em = new DomainMessage(app_key + "," + channelName);
        EventUtils.fireEvent(em, "getAppInfos");
        return em;

    }

//    public DomainMessage cheakAppKeyExits(){
//        DomainMessage em = new DomainMessage(app_key + "," + channelName);
//        EventUtils.fireEvent(em, "cheakAppKeyExits");
//        return em;
//    }


    public DomainMessage savePhoneInfo() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "savePhoneInfo");
        return em;

    }

    public DomainMessage getPhoneAttribute() {

        DomainMessage em = new DomainMessage(imsi);
        EventUtils.fireEvent(em, "getPhoneAttribute");
        return em;

    }

    public DomainMessage savePhoneAttribute(Area area) {

        DomainMessage em = new DomainMessage(area);
        EventUtils.fireEvent(em, "savePhoneAttribute");
        return em;

    }

    public DomainMessage cheakAppIsExit() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "cheakAppIsExit");
        return em;

    }


}
