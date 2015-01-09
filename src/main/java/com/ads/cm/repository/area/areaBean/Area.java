package com.ads.cm.repository.area.areaBean;

import com.ads.cm.annotation.Send;

import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.util.event.EventUtils;
import com.ads.cm.util.ip.IpUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Author: cyc
 * Date: 12-3-20
 * Time: 下午11:05
 * Description: to write something
 */
public class Area {

    private Integer provider_id;
    private Integer province_id;
    private Integer city_id;

    private String phone;
    private String smsp;
    private String ip;
    private String imsi;


    public Integer getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Integer provider_id) {
        this.provider_id = provider_id;
    }

    public Integer getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Integer province_id) {
        this.province_id = province_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsp() {
        return smsp;
    }

    public void setSmsp(String smsp) {
        this.smsp = smsp;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }


    @JsonIgnore
    public Integer getAreaId() {
        if (null != city_id) {
            return city_id;
        } else {
            return province_id;
        }
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).

                append("provider_id", provider_id).
                append("province_id", province_id).
                append("city_id", city_id).
                append("phone", phone).
                append("smsp", smsp).
                append("ip", ip).
                append("imsi", imsi).

                toString();
    }

    @Send("getAreaByPhone")
    @JsonIgnore
    public DomainMessage getAreaByPhone() {
        DomainMessage em;

        if (phone != null && phone.length() == 11) {
            em = new DomainMessage(phone.substring(0, 7));

        } else {
            em = new DomainMessage("");

        }

        EventUtils.fireEvent(em, "getAreaByPhone");
        return em;
    }

    @Send("getAreaBySmsp")
    @JsonIgnore
    public DomainMessage getAreaBySmsp() {
        DomainMessage em;

        if (smsp != null && smsp.length() == 11) {
            em = new DomainMessage(smsp.substring(0, 7));

        } else {
            em = new DomainMessage("");
        }

        EventUtils.fireEvent(em, "getAreaByPhone");                 //故意修改
        return em;
    }

    @Send("getAreaByIp")
    @JsonIgnore
    public DomainMessage getAreaByIp() {
        Long ipNum = IpUtils.stringIpToLong(ip);
        DomainMessage em = new DomainMessage(ipNum);
        EventUtils.fireEvent(em, "getAreaByIp");
        return em;
    }


}
