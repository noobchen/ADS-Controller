package com.ads.cm.strategy.area;


import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.util.phone.PhoneUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: cyc
 * Date: 12-3-22
 * Time: 上午10:40
 * Description: to write something
 */
public class IpStrategy implements AreaStrategy {
    private final Logger logger = LoggerFactory.getLogger(IpStrategy.class);

    @Override
    public Area getArea(Area conditionArea) {
        if (StringUtils.isNotEmpty(conditionArea.getIp())) {
            DomainMessage em = conditionArea.getAreaByIp();
            Area area = (Area) em.getEventResult();
            if (null != area && StringUtils.isNotEmpty(conditionArea.getImsi())) {
                int defaultProviderId = PhoneUtils.getProviderId(conditionArea.getImsi());
                if (defaultProviderId != area.getProvider_id()) {
                    logger.debug("user may be use wifi. defaultProviderId:{} area:{}.", defaultProviderId, area);
                    area.setProvider_id(defaultProviderId);
                    //这里是否还要考虑用全国的省份,如果用户是在漫游
                }

                logger.debug("find area:{} info by ip:{}.", area.toString(), conditionArea.getIp());
                return area;
            }
            return null;
        } else {
            return null;
        }
    }
}
