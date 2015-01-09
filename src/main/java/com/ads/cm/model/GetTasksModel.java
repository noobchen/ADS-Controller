package com.ads.cm.model;

import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.repository.getTask.taskBean.AdsTask;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import com.ads.cm.util.event.EventUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 下午1:37
 * To change this template use File | Settings | File Templates.
 */
public class GetTasksModel extends RegisterModel {

    private String ability;
    private String haveDownTaskId;
    private String showTimes;
    private String loginState;
    private String phoneIndex;
    private int model = 0;               //运行模式1Jar2APK默认是0兼容之前的版本
    @JsonIgnore
    private String ip;
    @JsonIgnore
    public List<AdsTask> tasks = new ArrayList<AdsTask>();
    @JsonIgnore
    private List<TaskInfos> chosenTask;
    @JsonIgnore
    private List<TaskInfos> infoList;
    @JsonIgnore
    private AppInfos appInfo;
    @JsonIgnore
    public int onWhatTask;
    @JsonIgnore
    public List<Integer> onWhatTaskList = new ArrayList<Integer>();

    public String getPhoneIndex() {
        return phoneIndex;
    }

    public void setPhoneIndex(String phoneIndex) {
        this.phoneIndex = phoneIndex;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public String getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(String showTimes) {
        this.showTimes = showTimes;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public AppInfos getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfos appInfo) {
        this.appInfo = appInfo;
    }


    public List<TaskInfos> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<TaskInfos> infoList) {
        this.infoList = infoList;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getHaveDownTaskId() {
        return haveDownTaskId;
    }

    public void setHaveDownTaskId(String haveDownTaskId) {
        this.haveDownTaskId = haveDownTaskId;
    }

    public List<AdsTask> getTasks() {
        return tasks;
    }

    public void addTasks(AdsTask tasks) {
        this.tasks.add(tasks);
    }

    public List<TaskInfos> getChosenTaskInfos() {
        return chosenTask;
    }


    public void setChosenTask(List<TaskInfos> chosenTask) {
        this.chosenTask = chosenTask;
    }

    public List<String> getHaveDownTaskIdList() {
        String[] tasks = haveDownTaskId.split(",");
        List<String> list = new ArrayList<String>();

        for (String temp : tasks) {
            list.add(temp);
        }

        return list;

    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ip:");
        stringBuilder.append(ip);
        stringBuilder.append("sdk_version:");
        stringBuilder.append(sdk_version);
        stringBuilder.append("app_key:");
        stringBuilder.append(app_key);
        stringBuilder.append("phoneNum:");
        stringBuilder.append(phoneNum);
        stringBuilder.append("imei:");
        stringBuilder.append(imei);
        stringBuilder.append("imsi:");
        stringBuilder.append(imsi);
        stringBuilder.append("phone_factory:");
        stringBuilder.append(phone_factory);
        stringBuilder.append("phone_version:");
        stringBuilder.append(phone_version);
//        stringBuilder.append("service_center:");
//        stringBuilder.append(service_center);
        stringBuilder.append("os_version:");
        stringBuilder.append(os_version);
        stringBuilder.append("packageName:");
        stringBuilder.append(packageName);
        stringBuilder.append("app_Name:");
        stringBuilder.append(app_Name);
        stringBuilder.append("ability:");
        stringBuilder.append(ability);
        stringBuilder.append("tasks:");
        stringBuilder.append(tasks);
        stringBuilder.append("infoList:");
        stringBuilder.append(infoList);
        stringBuilder.append("appInfo:");
        stringBuilder.append(appInfo);
        stringBuilder.append("onWhatTask:");
        stringBuilder.append(onWhatTask);
        stringBuilder.append("haveDownTaskId:");
        stringBuilder.append(haveDownTaskId);
        stringBuilder.append("loginState:");
        stringBuilder.append(loginState);
        stringBuilder.append("model:");
        stringBuilder.append(model);

        return stringBuilder.toString();
    }


    public String[] splitAbility() {
        return ability.split(",");
    }

    @Override
    public DomainMessage fireSelf() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "getTasksState");
        return em;

    }


    public DomainMessage getTasksInfoState() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "getTasksInfoState");
        return em;

    }




    public DomainMessage getAllTasks() {

        DomainMessage em = new DomainMessage(onWhatTask);
        EventUtils.fireEvent(em, "getAllTasks");
        return em;

    }


    public DomainMessage cheakPhoneAttribute() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "cheakPhoneAttribute");
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


    public DomainMessage selectTasks() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "selectTasks");
        return em;

    }


    public DomainMessage getTasksDetial() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "getTasksDetial");
        return em;

    }

    public DomainMessage getTasksDetialById(Integer id) {

        DomainMessage em = new DomainMessage(id);
        EventUtils.fireEvent(em, "getTasksDetialById");
        return em;

    }

    public void gC(){
        tasks = null;
        chosenTask =null;
        infoList =null;
        appInfo =null;
        onWhatTaskList =null;
    }
}
