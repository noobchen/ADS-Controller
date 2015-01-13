package com.ads.cm.state.TransmitResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Administrator on 2015/1/12.
 */
public class GetTaskStatusResponse {
    private GetTaskStatusResponse() {
    }

    private String resultCode;
    private List<GetTaskResponse> errorCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<GetTaskResponse> getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(List<GetTaskResponse> errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("resultCode", resultCode)
                .append("errorCode", errorCode)
                .toString();
    }
}
