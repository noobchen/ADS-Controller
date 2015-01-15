package com.ads.cm.state.register;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.ClientRequestModel;
import com.ads.cm.model.RegisterModel;
import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.strategy.area.AreaStrategy;
import com.ads.cm.util.datetime.DateTimeUtils;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;

import com.ads.cm.util.phone.PhoneUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 下午1:46
 * 注册统计用户资料
 */
@Consumer("registerState")
public class RegisterState implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(RegisterState.class);

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.


        HashMap<String, Object> response = new HashMap<String, Object>();    //返回客户端数据

        RegisterModel registerModel = (RegisterModel) event.getDomainMessage().getEventSource();
        logger.debug("client;{}  registerModel:{}", registerModel.getModelIp(), registerModel.toString());
        LogInstance.registerLogger.debug("client;{}  registerModel:{}", registerModel.getModelIp(), registerModel.toString());

        if (StringUtils.isEmpty(registerModel.getApp_key())) {                   //兼容之前版本未分配key异常
            registerModel.setApp_key("1-1-1");
        }

        long appIndex = (Long) registerModel.cheakAppInfoIsExit().getEventResult();

        if (appIndex == 0) {
            response.put("resultCode", "100");
            response.put("errorCode", "100");//无效的AppKey

            HttpUtils.response(registerModel, response);
            logger.debug("client;{} appkey:{} length >30 ", registerModel.getModelIp(), registerModel.app_key);
            LogInstance.registerLogger.debug("client;{} appkey:{} length >30 ", registerModel.getModelIp(), registerModel.app_key);
            return;
        }

        registerModel.setAppId(appIndex);

        long index = (Long) registerModel.savePhoneInfo().getEventResult();

        response.put("resultCode", "200");
        response.put("indexId", index);

        HttpUtils.response(registerModel, response);

        registerModel.transmitRegisterState();

    }


}
