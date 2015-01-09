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
 * Time: 上午10:41
 * Description: to write something
 */
public class PhoneStrategy implements AreaStrategy {
    private final Logger logger = LoggerFactory.getLogger(PhoneStrategy.class);

    @Override
    public Area getArea(Area conditionArea) {
        if (StringUtils.isNotEmpty(conditionArea.getPhone())) {

            DomainMessage dm = conditionArea.getAreaByPhone();
            Area area = (Area) dm.getEventResult();
            if (null != area && StringUtils.isNotEmpty(conditionArea.getImsi())) {
                //检查imsi卡和手机的对应运营商是否一致，不一致放弃手机号的判断
                int defaultProviderId = PhoneUtils.getProviderId(conditionArea.getImsi());
                if (defaultProviderId != area.getProvider_id()) {
                    return null;
                }
                logger.debug("find area:{} info by phone:{}.", area.toString(),conditionArea.getPhone());
                return area;
            } else {
                return null;
            }
        } else {
            return null;
        }


    }
}
