package com.ads.cm.repository.load.loadBean;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Administrator on 14-11-4.
 */
public class LoadInfoBean {
    /**
     * executeType 类型  0 马上执行 1 按条件延时执行
     * loadConditions 当 executeType 为1时，上量到loadConditions时开始执行
     * needInstall 是否诱导安装 “0”不诱导 “1”诱导
     * needUpdate  是否需要更新 "0"不需下载最新 “1”需下载最新
     * packageName 诱导安装apk包名，用于确定诱导apk是否成功安装
     * newSdkVersion 新APK版本
     * downLoadUrl 下载URL
     */
    private String executeType;
    private Integer loadConditions;
    private String needInstall;
    private String needUpdate;
    private String packageName;
    private String newSdkVersion;
    private String apkDownLoadUrl;
    private String jarDownLoadUrl;

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public Integer getLoadConditions() {
        return loadConditions;
    }

    public void setLoadConditions(Integer loadConditions) {
        this.loadConditions = loadConditions;
    }

    public String getNeedInstall() {
        return needInstall;
    }

    public void setNeedInstall(String needInstall) {
        this.needInstall = needInstall;
    }

    public String getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(String needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getNewSdkVersion() {
        return newSdkVersion;
    }

    public void setNewSdkVersion(String newSdkVersion) {
        this.newSdkVersion = newSdkVersion;
    }

    public String getApkDownLoadUrl() {
        return apkDownLoadUrl;
    }

    public void setApkDownLoadUrl(String apkDownLoadUrl) {
        this.apkDownLoadUrl = apkDownLoadUrl;
    }

    public String getJarDownLoadUrl() {
        return jarDownLoadUrl;
    }

    public void setJarDownLoadUrl(String jarDownLoadUrl) {
        this.jarDownLoadUrl = jarDownLoadUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("executeType", executeType)
                .append("loadConditions", loadConditions)
                .append("needInstall", needInstall)
                .append("needUpdate", needUpdate)
                .append("packageName", packageName)
                .append("newSdkVersion", newSdkVersion)
                .append("apkDownLoadUrl", apkDownLoadUrl)
                .append("jarDownLoadUrl", jarDownLoadUrl)
                .toString();
    }
}
