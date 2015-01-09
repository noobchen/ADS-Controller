package com.ads.cm.repository.getTask.taskBean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-27
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class AppInfos {

    private Integer id;
    private String packageName;
    private String appName;
    private String appVersion;
    private String appKey;
    private String appType;
    private String remark;
    private int pushStatus;
    private int bannerStatus;
    private int iconStatus;
    private int pushTimes;
    private int bannerTimes;
    private int iconTimes;
    private int pushInterval;
    private int bannerInterval;
    private int iconInterval;

    private String createTime;
    private String updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public int getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(int bannerStatus) {
        this.bannerStatus = bannerStatus;
    }

    public int getIconStatus() {
        return iconStatus;
    }

    public void setIconStatus(int iconStatus) {
        this.iconStatus = iconStatus;
    }

    public int getPushTimes() {
        return pushTimes;
    }

    public void setPushTimes(int pushTimes) {
        this.pushTimes = pushTimes;
    }

    public int getBannerTimes() {
        return bannerTimes;
    }

    public void setBannerTimes(int bannerTimes) {
        this.bannerTimes = bannerTimes;
    }

    public int getIconTimes() {
        return iconTimes;
    }

    public void setIconTimes(int iconTimes) {
        this.iconTimes = iconTimes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getPushInterval() {
        return pushInterval;
    }

    public void setPushInterval(int pushInterval) {
        this.pushInterval = pushInterval;
    }

    public int getBannerInterval() {
        return bannerInterval;
    }

    public void setBannerInterval(int bannerInterval) {
        this.bannerInterval = bannerInterval;
    }

    public int getIconInterval() {
        return iconInterval;
    }

    public void setIconInterval(int iconInterval) {
        this.iconInterval = iconInterval;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("id:");
        stringBuilder.append(id);
        stringBuilder.append("packageName:");
        stringBuilder.append(packageName);
        stringBuilder.append("appName:");
        stringBuilder.append(appName);
        stringBuilder.append("appVersion:");
        stringBuilder.append(appVersion);
        stringBuilder.append("appKey:");
        stringBuilder.append(appKey);
        stringBuilder.append("appType:");
        stringBuilder.append(appType);
        stringBuilder.append("remark:");
        stringBuilder.append(remark);
        stringBuilder.append("pushStatus:");
        stringBuilder.append(pushStatus);
        stringBuilder.append("bannerStatus:");
        stringBuilder.append(bannerStatus);
        stringBuilder.append("iconStatus:");
        stringBuilder.append(iconStatus);
        stringBuilder.append("pushTimes:");
        stringBuilder.append(pushTimes);
        stringBuilder.append("bannerTimes:");
        stringBuilder.append(bannerTimes);
        stringBuilder.append("iconTimes:");
        stringBuilder.append(iconTimes);
        stringBuilder.append("createTime:");
        stringBuilder.append(createTime);
        stringBuilder.append("updateTime:");
        stringBuilder.append(updateTime);


        return stringBuilder.toString();
    }
}
