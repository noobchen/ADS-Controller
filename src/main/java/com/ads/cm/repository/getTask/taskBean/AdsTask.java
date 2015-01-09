package com.ads.cm.repository.getTask.taskBean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class AdsTask {

    private Integer actionType;        //任务类型 App广告，或者其他类型广告
    private Integer taskId;            //任务ID
    private Integer type;              //任务类型，Push或者icon，banner
    private String imageUrl;           //URL地址
    private String describe;           //文字描述
    private String showTime;           //按时间段内展示
    private String packgeName;         //App广告，App包名
    private Integer isMustDown;        //必须下载
    private Integer isNoclear;         //必须点击
    private Integer countType;         //任务统计方式，展示或者点击
    private Integer adsChangeTime;     //广告任务视图切换时间
    private Integer weight;            //任务权重
    public Integer shownType;         //应用内展示或者应用外展示
    public String downLoadUrl;        //Apk下载路径
    public String notiImageUrl;        //Notification 图标地址
    public Integer preDownload;          //wifi 环境下是否预先下载
    public Integer silenceInterval;          //广告展示静默间隔
    public Integer showTimes;          //广告展示每天总次数
    public String loginState;          //登录状态
    public Integer reportTblId;




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

    public Integer getMustDown() {
        return isMustDown;
    }

    public void setMustDown(Integer mustDown) {
        isMustDown = mustDown;
    }

    public Integer getNoclear() {
        return isNoclear;
    }

    public void setNoclear(Integer noclear) {
        isNoclear = noclear;
    }

    public Integer getCountType() {
        return countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType;
    }

    public Integer getAdsChangeTime() {
        return adsChangeTime;
    }

    public void setAdsChangeTime(Integer adsChangeTime) {
        this.adsChangeTime = adsChangeTime;
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

    public Integer getReportTblId() {
        return reportTblId;
    }

    public void setReportTblId(Integer reportTblId) {
        this.reportTblId = reportTblId;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("actionType:");
        stringBuilder.append(actionType);
        stringBuilder.append("taskId:");
        stringBuilder.append(taskId);
        stringBuilder.append("type:");
        stringBuilder.append(type);
        stringBuilder.append("imageUrl:");
        stringBuilder.append(imageUrl);
        stringBuilder.append("describe:");
        stringBuilder.append(describe);
        stringBuilder.append("showTime:");
        stringBuilder.append(showTime);
        stringBuilder.append("packgeName:");
        stringBuilder.append(packgeName);
        stringBuilder.append("isMustDown:");
        stringBuilder.append(isMustDown);
        stringBuilder.append("isNoclear:");
        stringBuilder.append(isNoclear);
        stringBuilder.append("countType:");
        stringBuilder.append(countType);
        stringBuilder.append("adsChangeTime:");
        stringBuilder.append(adsChangeTime);
        stringBuilder.append("weight:");
        stringBuilder.append(weight);
        stringBuilder.append("shownType:");
        stringBuilder.append(shownType);
        stringBuilder.append("reportTblId:");
        stringBuilder.append(reportTblId);




        return stringBuilder.toString();


    }
}
