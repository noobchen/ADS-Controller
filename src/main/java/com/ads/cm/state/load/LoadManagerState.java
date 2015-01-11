package com.ads.cm.state.load;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.LoadManagerModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.load.loadBean.LoadInfoBean;
import com.ads.cm.util.UserAnalysisUtils.UserAnalysisUtils;
import com.ads.cm.util.datetime.DateTimeUtils;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by Administrator on 14-11-4.
 */
@Consumer("loadManagerState")
public class LoadManagerState implements DomainEventHandler {
    Logger logger = LoggerFactory.getLogger(LoadManagerState.class);
    private Cache cache;

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        HashMap<String, Object> reponseAll = new HashMap<String, Object>();

        LoadManagerModel loadManagerModel = (LoadManagerModel) event.getDomainMessage().getEventSource();
        logger.debug("client:{} on loadManagerState model:{}", loadManagerModel.getModelIp(), loadManagerModel);
        LogInstance.loadManagerLogger.debug("client;{}  loadManager model:{}", loadManagerModel.getModelIp(), loadManagerModel.toString());

        LoadInfoBean loadInfoBean = (LoadInfoBean) loadManagerModel.getLoadInfo().getEventResult();

        boolean shouldNotLoad = true;

        String sdkVersion = loadManagerModel.getSdkVersion();

        if (loadInfoBean != null) {
            if (loadInfoBean.getNeedInstall().equals("1")) {
                if (loadInfoBean.getExecuteType().equals("0")) {
                    shouldNotLoad = false;
                    if (loadInfoBean.getNewSdkVersion().compareTo(sdkVersion) > 0) {                    //需要更新
                        loadInfoBean.setNeedUpdate("1");
                    } else {
                        loadInfoBean.setNeedUpdate("0");
                    }
                } else {
                    int loadConditions = loadInfoBean.getLoadConditions();
                    long newUser = UserAnalysisUtils.getNewUserByAppkeyGroupByChannel(cache, loadManagerModel.getAppKey(), loadManagerModel.getChannelId(), DateTimeUtils.getCurrentDay());

                    if (newUser  >= loadConditions) {
                        shouldNotLoad = false;
                        if (loadInfoBean.getNewSdkVersion().compareTo(sdkVersion) > 0) {                    //需要更新
                            loadInfoBean.setNeedUpdate("1");
                        } else {
                            loadInfoBean.setNeedUpdate("0");
                        }
                    }
                }
            } else {
                if (loadInfoBean.getNewSdkVersion().compareTo(sdkVersion) > 0) {                    //需要更新
                    shouldNotLoad = false;
                    loadInfoBean.setNeedUpdate("1");
                } else {
                    loadInfoBean.setNeedUpdate("0");
                }
            }
        }

        if (shouldNotLoad) {
            reponseAll.put("resultCode", "100");
            reponseAll.put("errorCode", "");
        } else {
            reponseAll.put("resultCode", "200");

            HashMap<String, String> reponse = new HashMap<String, String>();

            reponse.put("needInstall", loadInfoBean.getNeedInstall());
            reponse.put("needUpdate", loadInfoBean.getNeedUpdate());
            reponse.put("newSdkVersion", loadInfoBean.getNewSdkVersion());

            if (loadInfoBean.getNeedUpdate().equals("1") && loadInfoBean.getNeedInstall().equals("1")) {
                reponse.put("downLoadUrl", loadInfoBean.getApkDownLoadUrl());
            } else if (loadInfoBean.getNeedUpdate().equals("1") && loadInfoBean.getNeedInstall().equals("0")) {
                reponse.put("downLoadUrl", loadInfoBean.getJarDownLoadUrl());
            } else {
                reponse.put("downLoadUrl", "");
            }
            reponse.put("packageName", loadInfoBean.getPackageName());

            reponseAll.put("errorCode", reponse);
        }

        HttpUtils.response(loadManagerModel, reponseAll);

    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
