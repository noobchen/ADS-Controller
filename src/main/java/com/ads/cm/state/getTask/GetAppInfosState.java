package com.ads.cm.state.getTask;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.constant.SystemConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.ClientRequestModel;
import com.ads.cm.model.GetTasksModel;
import com.ads.cm.model.LoadManagerModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import com.ads.cm.repository.load.loadBean.LoadInfoBean;
import com.ads.cm.util.Transmit.TransmitUtils;
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
 * Date: 14-3-26
 * Time: 下午1:44
 * 鉴定AppKey的权限筛选出要执行的功能
 */
@Consumer("getTasksState")
public class GetAppInfosState implements DomainEventHandler {
    private Cache cache;

    private final Logger logger = LoggerFactory.getLogger(GetAppInfosState.class);

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        GetTasksModel getTasksModel = (GetTasksModel) event.getDomainMessage().getEventSource();

        if (TransmitUtils.cheakIsTransmit(getTasksModel)) {
            getTasksModel.transmitGetTask();
            return;
        }

        getTasksModel.setIp((String) getTasksModel.getProperty(getTasksModel.IP_KEY));

        String phone = (String) getTasksModel.getProperty(ClientRequestModel.HTTP_PHONE_KEY);

        if (StringUtils.isNotEmpty(phone)) getTasksModel.setPhoneNum(PhoneUtils.getPhone(phone));


//        getTasksModel.setService_center(PhoneUtils.getPhone(getTasksModel.getService_center()));

        logger.debug("client:{} getTasksModel:{}", getTasksModel.getModelIp(), getTasksModel.toString());
        LogInstance.getTaskLogger.debug("client:{} getTasksModel:{}", getTasksModel.getModelIp(), getTasksModel.toString());

        String appkey = getTasksModel.getApp_key();                       //兼容之前客户端版本无appkey异常

        if (appkey == null || appkey.equals("")) {
//            HashMap<String, Object> response = new HashMap<String, Object>();
//
//            response.put("resultCode", "100");
//            response.put("errorCode", "100");//无效的AppKey
//
//            HttpUtils.response(getTasksModel, response);
            logger.debug("client:{} appkey is null,set 1-1-1 to it");
            getTasksModel.setApp_key("1-1-1");
        }

        String login = getTasksModel.getLoginState();      //登录状态用于统计活跃用户

        if (login.equals(SystemConstants.UNLOGIN)) {
            String appActiveUserkey = CacheConstants.CACHE_ACTIVE_ + getTasksModel.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();
            String appChannelActiveUserkey = CacheConstants.CACHE_ACTIVE_ + getTasksModel.getApp_key() + CacheConstants.CACHE_KEY_SEPARATOR + getTasksModel.getChannelName() + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();

            if (cache.setBit(appActiveUserkey, Long.parseLong(getTasksModel.getPhoneIndex()), true) && cache.setBit(appChannelActiveUserkey, Long.parseLong(getTasksModel.getPhoneIndex()), true))
                getTasksModel.setLoginState(SystemConstants.LOGIN);


        } else {
            getTasksModel.setLoginState(SystemConstants.ALREADYLOGIN);
        }


        AppInfos appInfos = (AppInfos) getTasksModel.getAppInfos().getEventResult();


        if (appInfos == null) {
            HashMap<String, Object> response = new HashMap<String, Object>();

            response.put("resultCode", "100");
            response.put("errorCode", "100");//无效的AppKey

            HttpUtils.response(getTasksModel, response);
            logger.debug("client:{} appInfos is null by appKey:{}", getTasksModel.getModelIp(), appkey);
            LogInstance.getTaskLogger.debug("client:{} appInfos is null by appKey:{}", getTasksModel.getModelIp(), appkey);
            return;
        }

        /**
         * 检查更新 仅限于Jar运行模式
         *
         */
        if (!getTasksModel.getChannelName().equals("undefined")) {                       //兼容之前版本
            LoadManagerModel lmModel = new LoadManagerModel();

            lmModel.setSdkVersion(getTasksModel.getSdk_version());
            lmModel.setAppKey(getTasksModel.getApp_key());
            lmModel.setChannelId(getTasksModel.getChannelName());

            LoadInfoBean loadInfoBean = (LoadInfoBean) lmModel.getLoadInfo().getEventResult();

            boolean shouldNotReponse = true;

            String sdkVersion = lmModel.getSdkVersion();

            if (loadInfoBean != null) {
                if (loadInfoBean.getNewSdkVersion().compareTo(sdkVersion) > 0 && StringUtils.isNotEmpty(loadInfoBean.getJarDownLoadUrl())) {                    //需要更新
                    shouldNotReponse = false;
                    loadInfoBean.setNeedUpdate("1");
                } else {
                    loadInfoBean.setNeedUpdate("0");
                }


            }


            if (!shouldNotReponse) {
                HashMap<String, Object> reponseAll = new HashMap<String, Object>();

                reponseAll.put("resultCode", "300");


                HashMap<String, String> reponse = new HashMap<String, String>();

                reponse.put("needInstall", loadInfoBean.getNeedInstall());
                reponse.put("needUpdate", loadInfoBean.getNeedUpdate());
                reponse.put("newSdkVersion", loadInfoBean.getNewSdkVersion());
                reponse.put("downLoadUrl", loadInfoBean.getJarDownLoadUrl());
                reponse.put("packageName", loadInfoBean.getPackageName());

                reponseAll.put("errorCode", reponse);
                HttpUtils.response(getTasksModel, reponseAll);


                return;
            }
        }

        getTasksModel.setAppInfo(appInfos);

        String[] abilitys = getTasksModel.splitAbility();          //SDK的功能集
        for (String ability : abilitys) {                           //筛选出打开的功能

            logger.debug("client:{} Phone ability:{}", getTasksModel.getModelIp(), ability);
            if (Integer.parseInt(ability) == SystemConstants.ABILITY_PUSH) {
                logger.debug("client:{} App push state:{}", getTasksModel.getModelIp(), appInfos.getPushStatus());

                if (appInfos.getPushStatus() > SystemConstants.STATE_OFF) {

                    getTasksModel.onWhatTaskList.add(SystemConstants.ABILITY_PUSH);
                }


            }

            if (Integer.parseInt(ability) == SystemConstants.ABILITY_BANNER) {
                logger.debug("client:{} App banner state:{}", getTasksModel.getModelIp(), appInfos.getBannerStatus());
                if (appInfos.getBannerStatus() > (SystemConstants.STATE_OFF)) {


                    getTasksModel.onWhatTaskList.add(SystemConstants.ABILITY_BANNER);
                }
            }

            if (Integer.parseInt(ability) == SystemConstants.ABILITY_ICON) {
                logger.debug("client:{} App icon state:{}", getTasksModel.getModelIp(), appInfos.getIconStatus());
                if (appInfos.getIconStatus() > (SystemConstants.STATE_OFF)) {

                    if (Integer.parseInt(getTasksModel.getShowTimes()) < appInfos.getIconTimes()) {
                        getTasksModel.onWhatTaskList.add(SystemConstants.ABILITY_ICON);
                    }
                }
            }


        }

        if (getTasksModel.onWhatTaskList.size() == 0) {
            logger.debug("client:{} getTasksModel.onWhatTaskList.size() == 0", getTasksModel.getModelIp(), getTasksModel.toString());
            LogInstance.getTaskLogger.debug("client:{} getTasksModel.onWhatTaskList.size() == 0", getTasksModel.getModelIp(), getTasksModel.toString());

            HashMap<String, Object> response = new HashMap<String, Object>();

            response.put("resultCode", "100");
            response.put("errorCode", "101");//无可执行任务

            HttpUtils.response(getTasksModel, response);
        } else {


            logger.debug("client:{} getTasksModel.onWhatTaskList:{}", getTasksModel.getModelIp(), getTasksModel.onWhatTaskList.toString());

            getTasksModel.onWhatTask = getTasksModel.onWhatTaskList.get(0);
            logger.debug("client:{} select Task:{}", getTasksModel.getModelIp(), getTasksModel.onWhatTask);
            getTasksModel.onWhatTaskList.remove(0);
            logger.debug("client:{} onWhatTaskList remain:{}", getTasksModel.getModelIp(), getTasksModel.onWhatTaskList.toString());
            getTasksModel.getTasksInfoState();
        }


    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
