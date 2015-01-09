package com.ads.cm.strategy.area;


import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.util.phone.PhoneUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: cyc
 * Date: 12-3-22
 * Time: 上午10:41
 * Description: to write something
 */
public class SmspStrategy implements AreaStrategy {
    private final Logger logger = LoggerFactory.getLogger(SmspStrategy.class);

    @Override
    public Area getArea(Area conditionArea) {
        if (StringUtils.isNotEmpty(conditionArea.getSmsp())) {


            Area area = (Area) conditionArea.getAreaBySmsp().getEventResult();

            if (null != area && StringUtils.isNotEmpty(conditionArea.getImsi())) {
                int defaultProviderId = PhoneUtils.getProviderId(conditionArea.getImsi());
                if (defaultProviderId != area.getProvider_id()) {
                    logger.debug("defaultProviderId:{} area:{} isn't equals.", defaultProviderId, conditionArea);
                    area.setProvider_id(defaultProviderId);

                }
                logger.debug("find area:{} info by smsp:{}.", area.toString(), conditionArea.getSmsp());
                return area;
            } else {
                return null;
            }

        } else {
            return null;
        }
    }
}
