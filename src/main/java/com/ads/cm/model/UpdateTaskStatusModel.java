package com.ads.cm.model;

import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.util.event.EventUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class UpdateTaskStatusModel extends ClientRequestModel {
    private Integer linkId;

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    @Override
    public DomainMessage fireSelf() {
        DomainMessage em = new DomainMessage(this);
        EventUtils.fireEvent(em, "updateInstallStatus");
        return em;
    }
}
