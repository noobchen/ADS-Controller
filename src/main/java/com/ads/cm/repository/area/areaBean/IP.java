package com.ads.cm.repository.area.areaBean;

public class IP {
    private long ip_start_num;
    private long ip_end_num;
    private Integer province_id;
    private Integer provider_id;
    private Integer city_id;

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public long getIp_end_num() {
        return ip_end_num;
    }

    public void setIp_end_num(long ip_end_num) {
        this.ip_end_num = ip_end_num;
    }

    public long getIp_start_num() {
        return ip_start_num;
    }

    public void setIp_start_num(long ip_start_num) {
        this.ip_start_num = ip_start_num;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("city_id:").append(city_id);
        sb.append("|ip_start_num:").append(ip_start_num);
        sb.append("|ip_end_num:").append(ip_end_num);
        sb.append("|province_id:").append(province_id);
        sb.append("|provider_id:").append(provider_id);
        return sb.toString();
    }
}
