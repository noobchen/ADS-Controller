package com.ads.cm.repository.register;

import com.ads.cm.annotation.Component;
import com.ads.cm.annotation.OnEvent;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.constant.ProviderConstants;
import com.ads.cm.model.ClientRequestModel;
import com.ads.cm.model.RegisterModel;
import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.repository.cache.Cache;

import com.ads.cm.strategy.area.AreaStrategy;
import com.ads.cm.util.datetime.DateTimeUtils;
import com.ads.cm.util.json.JsonUtils;

import com.ads.cm.util.phone.PhoneUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PhoneRegisterRepositoryImpl implements PhoneRegisterRepository {
    private final Logger logger = LoggerFactory.getLogger(PhoneRegisterRepositoryImpl.class);

    private Cache cache;
    private PhoneRegisterDao phoneRegisterDao;
    private List<AreaStrategy> areaStrategies;


    @Override
    @OnEvent("cheakAppInfoIsExit")
    public Long cheakAppInfoIsExit(RegisterModel model) {
        return phoneRegisterDao.cheakAppIsExits(model);
    }

    public Long cheakUserInfoIsExit(RegisterModel model) {
        if (StringUtils.isNotEmpty(model.getImei())) {
            return phoneRegisterDao.cheakUserInfoIsExits(model);
        } else {
            return 0l;
        }
    }

    @Override
    @OnEvent("savePhoneInfo")
    public Long savePhoneInfo(RegisterModel model) {

        long id = cheakUserInfoIsExit(model);

        if (id == 0) {          //不存在历史数据，新增数据
            logger.debug("client;{} this client dont registered !", model.getModelIp());

            //判断是否新增渠道信息
            long index = phoneRegisterDao.cheakAppChannelIsExits(model);

            if (index == 0) {
                if (model.getAppId() == null) {
                    phoneRegisterDao.getAppId(model);
                }
                phoneRegisterDao.addAppChannelInfo(model);
            }

            String phone = (String) model.getProperty(ClientRequestModel.HTTP_PHONE_KEY);
            if (StringUtils.isNotEmpty(phone)) {
                model.setPhoneNum(PhoneUtils.getPhone(phone));
            }

            Area area = null;                   //查詢出用戶的歸屬地信息

            Area conditionArea = new Area();
            conditionArea.setPhone(model.getPhoneNum());
            conditionArea.setSmsp("");
            conditionArea.setIp(model.getModelIp());
            conditionArea.setImsi(model.getImsi());


            for (AreaStrategy areaStrategy : areaStrategies) {
                area = areaStrategy.getArea(conditionArea);

                if (null != area) {
                    area.setImsi(model.getImsi());
                    logger.debug("client:{} find area:{} by strategy", model.getModelIp(), area.toString());

                    break;
                }
            }

            //通过策略没有找到用户的归属地信息,则设置默认情况
            if (area == null) {
                area = new Area();

                area.setImsi(model.getImsi());
                area.setProvince_id(ProviderConstants.CHINA_ALL_PROVINCE);
                area.setCity_id(ProviderConstants.CHINA_ALL_CITY);
                area.setProvider_id(PhoneUtils.getProviderId(conditionArea.getImsi()));

                logger.debug("client;{} find area:{} by default", model.getModelIp(), area.toString());

            }


            model.area = area;

            String json = JsonUtils.objectToJson(area);


            cache.set(CacheConstants.AREA_IMSI_PREFIX + model.imsi, json);
            cache.setTimeout(CacheConstants.AREA_IMSI_PREFIX + model.imsi, 30 * 24 * 60 * 60);

            logger.debug("client;{} finished put area json:{}  to redis ", model.getModelIp(), json);


            id = phoneRegisterDao.savePhoneInfo(model);
            logger.debug("client:{} finished register phone by id:{}", model.getModelIp(), id);

            //统计新增用户  活跃用户
            String appNewUserKey = CacheConstants.CACHE_NEW__ + model.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();
            String appChannelNewUserKey = CacheConstants.CACHE_NEW__ + model.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + model.getChannelName() + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();

            String appActiveUserkey = CacheConstants.CACHE_ACTIVE_ + model.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();
            String appChannelActiveUserkey = CacheConstants.CACHE_ACTIVE_ + model.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + model.getChannelName() + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();

            cache.setBit(appNewUserKey, id, true);
            cache.setBit(appChannelNewUserKey, id, true);
            cache.setBit(appActiveUserkey, id, true);
            cache.setBit(appChannelActiveUserkey, id, true);

            cache.setTimeout(appNewUserKey, 60 * 24 * 60 * 60);
            cache.setTimeout(appChannelNewUserKey, 60 * 24 * 60 * 60);
            cache.setTimeout(appActiveUserkey, 60 * 24 * 60 * 60);
            cache.setTimeout(appChannelActiveUserkey, 60 * 24 * 60 * 60);
            logger.debug("client:{} finished add new user phone by appNewUserKey:{} appChannelNewUserKey:{} id:{}", new Object[]{model.getModelIp(), appNewUserKey, appChannelNewUserKey, id});

        }

        return id;


    }

    public void setPhoneRegisterDao(PhoneRegisterDao phoneRegisterDao) {
        this.phoneRegisterDao = phoneRegisterDao;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void setAreaStrategies(List<AreaStrategy> areaStrategies) {
        this.areaStrategies = areaStrategies;
    }
}
