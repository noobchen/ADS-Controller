package com.ads.cm.repository.area;

import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.repository.area.areaBean.IP;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-28
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */
public interface AreaRepository {
    public Area getAreaByPhone(String phonePrefix);

    public Area getAreaBySmsp(String smsp);

    public Area getAreaByIp(Long ip);

    public Area getPhoneAttribute(String imsi);

    public void savePhoneAttribute(Area area);

}
