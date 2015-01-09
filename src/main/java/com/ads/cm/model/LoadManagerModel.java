package com.ads.cm.model;

import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.util.event.EventUtils;

/**
 * Created by Administrator on 14-11-4.
 */
public class LoadManagerModel extends ClientRequestModel {
    private String appKey;
    private String channelId;
    private String sdkVersion;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }


    @Override
    public String toString() {
        return "LoadManagerModel{" +
                "appKey='" + appKey + '\'' +
                ", channelId='" + channelId + '\'' +
                ", sdkVersion='" + sdkVersion + '\'' +
                '}';
    }

    @Override
    public DomainMessage fireSelf() {
        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "loadManagerState");
        return em;
    }

    public DomainMessage getLoadInfo() {
        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "cheakUpdate");

        return em;
    }
}
