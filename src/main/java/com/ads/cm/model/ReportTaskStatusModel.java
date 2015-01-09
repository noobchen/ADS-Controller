package com.ads.cm.model;

import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.util.event.EventUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class ReportTaskStatusModel extends ClientRequestModel {
    private Integer id;
    private Integer phoneIndex;
    private Integer taskId;

    private Integer showState;
    private Integer downState;
    private Integer installState;
    private Integer reportTblId;
    private Integer getTaskState;
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getGetTaskState() {
        return getTaskState;
    }

    public void setGetTaskState(Integer getTaskState) {
        this.getTaskState = getTaskState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhoneIndex() {
        return phoneIndex;
    }

    public void setPhoneIndex(Integer phoneIndex) {
        this.phoneIndex = phoneIndex;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getShowState() {
        return showState;
    }

    public void setShowState(Integer showState) {
        this.showState = showState;
    }

    public Integer getDownState() {
        return downState;
    }

    public void setDownState(Integer downState) {
        this.downState = downState;
    }

    public Integer getInstallState() {
        return installState;
    }

    public void setInstallState(Integer installState) {
        this.installState = installState;
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

        stringBuilder.append("phoneIndex:");
        stringBuilder.append(phoneIndex);
        stringBuilder.append("taskId:");
        stringBuilder.append(taskId);
        stringBuilder.append("showState:");
        stringBuilder.append(showState);
        stringBuilder.append("downState:");
        stringBuilder.append(downState);
        stringBuilder.append("installState:");
        stringBuilder.append(installState);
        stringBuilder.append("reportTblId:");
        stringBuilder.append(reportTblId);

        return stringBuilder.toString();
    }

    public DomainMessage getTaskInfoByTaskId() {

        DomainMessage em = new DomainMessage(taskId);
        EventUtils.fireEvent(em, "getTaskInfoByTaskId");
        return em;

    }

    @Override
    public DomainMessage fireSelf() {

        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "reportTaskStatusState");
        return em;
    }
}
