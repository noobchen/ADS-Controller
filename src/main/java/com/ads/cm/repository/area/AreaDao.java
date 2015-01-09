package com.ads.cm.repository.area;

import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.repository.area.areaBean.IP;

import java.util.List;

/**
 * Author: cyc
 * Date: 12-3-20
 * Time: 下午11:15
 * Description: to write something
 */
public interface AreaDao {
    public Area getPhoneAttribute(String imsi);

    public void savePhoneAttribute(Area area);

    public Area getAreaByPhone(String phonePrefix);

    public Area getAreaBySmsp(String smsp);

    public Area getAreaByIp(Long ip);

    public List<IP> getAllIp();


}
