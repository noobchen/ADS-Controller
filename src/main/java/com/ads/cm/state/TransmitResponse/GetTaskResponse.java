package com.ads.cm.state.TransmitResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Administrator on 2015/1/12.
 */
public class GetTaskResponse {
    private GetTaskResponse() {
    }

    Integer actionType;
    Integer taskId;
    Integer type;
    String imageUrl;
    String describe;
    String showTime;
    String packgeName;
    Integer countType;
    Integer mustDown;
    Integer noclear;
    Integer weight;
    Integer shownType;
    Integer adsChangeTime;
    String downLoadUrl;
    String notiImageUrl;
    Integer preDownload;
    Integer silenceInterval;
    Integer showTimes;
    String loginState;
    Integer reportTblId;


    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getPackgeName() {
        return packgeName;
    }

    public void setPackgeName(String packgeName) {
        this.packgeName = packgeName;
    }

    public Integer getCountType() {
        return countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType;
    }

    public Integer getMustDown() {
        return mustDown;
    }

    public void setMustDown(Integer mustDown) {
        this.mustDown = mustDown;
    }

    public Integer getNoclear() {
        return noclear;
    }

    public void setNoclear(Integer noclear) {
        this.noclear = noclear;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getShownType() {
        return shownType;
    }

    public void setShownType(Integer shownType) {
        this.shownType = shownType;
    }

    public Integer getAdsChangeTime() {
        return adsChangeTime;
    }

    public void setAdsChangeTime(Integer adsChangeTime) {
        this.adsChangeTime = adsChangeTime;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getNotiImageUrl() {
        return notiImageUrl;
    }

    public void setNotiImageUrl(String notiImageUrl) {
        this.notiImageUrl = notiImageUrl;
    }

    public Integer getPreDownload() {
        return preDownload;
    }

    public void setPreDownload(Integer preDownload) {
        this.preDownload = preDownload;
    }

    public Integer getSilenceInterval() {
        return silenceInterval;
    }

    public void setSilenceInterval(Integer silenceInterval) {
        this.silenceInterval = silenceInterval;
    }

    public Integer getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(Integer showTimes) {
        this.showTimes = showTimes;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public Integer getReportTblId() {
        return reportTblId;
    }

    public void setReportTblId(Integer reportTblId) {
        this.reportTblId = reportTblId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("actionType", actionType)
                .append("taskId", taskId)
                .append("type", type)
                .append("imageUrl", imageUrl)
                .append("describe", describe)
                .append("showTime", showTime)
                .append("packgeName", packgeName)
                .append("countType", countType)
                .append("mustDown", mustDown)
                .append("noclear", noclear)
                .append("weight", weight)
                .append("shownType", shownType)
                .append("adsChangeTime", adsChangeTime)
                .append("downLoadUrl", downLoadUrl)
                .append("notiImageUrl", notiImageUrl)
                .append("preDownload", preDownload)
                .append("silenceInterval", silenceInterval)
                .append("showTimes", showTimes)
                .append("loginState", loginState)
                .append("reportTblId", reportTblId)
                .toString();
    }
}
