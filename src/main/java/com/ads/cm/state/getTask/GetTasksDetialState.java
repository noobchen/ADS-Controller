package com.ads.cm.state.getTask;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.constant.SystemConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.GetTasksModel;
import com.ads.cm.model.ReportTaskStatusModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.getTask.GetTasksDao;
import com.ads.cm.repository.getTask.taskBean.AdsTask;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import com.ads.cm.util.datetime.DateTimeUtils;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-28
 * Time: 下午4:31
 * 查询所选取任务的详细信息
 */
@Consumer("getTasksDetial")
public class GetTasksDetialState implements DomainEventHandler {
    private Cache cache;
    private GetTasksDao dao;
    private final Logger logger = LoggerFactory.getLogger(GetAppInfosState.class);

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        GetTasksModel getTasksModel = (GetTasksModel) event.getDomainMessage().getEventSource();
        logger.debug("client:{} onWhatTask:{} GetTasksDetialState", getTasksModel.getModelIp(), getTasksModel.onWhatTask);

        List<TaskInfos> chosenTaskInfos = getTasksModel.getChosenTaskInfos();

        if (chosenTaskInfos.size() == 0) {
            logger.debug("client:{} chosenTaskInfos list is null");

            if (getTasksModel.onWhatTaskList.size() == 0) {
                if (getTasksModel.tasks.size() == 0) {
                    logger.debug("client:{} donot have task to execute !", getTasksModel.getModelIp());
                    LogInstance.getTaskLogger.debug("client:{} donot have task to execute !", getTasksModel.getModelIp());

                    HashMap<String, Object> response = new HashMap<String, Object>();

                    response.put("resultCode", "100");
                    response.put("errorCode", "101");//无可执行任务
                    HttpUtils.response(getTasksModel, response);
                } else {

                    HashMap<String, Object> response = new HashMap<String, Object>();

                    response.put("resultCode", "200");
                    response.put("errorCode", getTasksModel.getTasks());

                    HttpUtils.response(getTasksModel, response);
                }


            } else {
                getTasksModel.onWhatTask = getTasksModel.onWhatTaskList.get(0);
                logger.debug("client:{} select Task:{}", getTasksModel.getModelIp(), getTasksModel.onWhatTask);
                getTasksModel.onWhatTaskList.remove(0);
                logger.debug("client:{} onWhatTaskList remain:{}", getTasksModel.getModelIp(), getTasksModel.onWhatTaskList.toString());
                getTasksModel.getTasksInfoState();
            }

            return;
        }

        for (TaskInfos info : chosenTaskInfos) {
            logger.debug("client:{} chosenTaskInfos:{}", getTasksModel.getModelIp(), info.toString());
            Integer taskId = info.getId();

            AdsTask adsTask = (AdsTask) getTasksModel.getTasksDetialById(taskId).getEventResult();

            adsTask.setTaskId(info.getId());

            int type = info.getType();

            adsTask.setType(type);

            switch (type) {
                case SystemConstants.ABILITY_PUSH:
                    adsTask.shownType = getTasksModel.getAppInfo().getPushStatus();
                    adsTask.silenceInterval = getTasksModel.getAppInfo().getPushInterval();
                    adsTask.showTimes = getTasksModel.getAppInfo().getPushTimes();
                    break;
                case SystemConstants.ABILITY_BANNER:
                    adsTask.shownType = getTasksModel.getAppInfo().getBannerStatus();
                    adsTask.silenceInterval = getTasksModel.getAppInfo().getBannerInterval();
                    adsTask.showTimes = getTasksModel.getAppInfo().getBannerTimes();
                    break;
                case SystemConstants.ABILITY_ICON:
                    adsTask.shownType = getTasksModel.getAppInfo().getIconStatus();
                    adsTask.silenceInterval = getTasksModel.getAppInfo().getIconInterval();
                    adsTask.showTimes = getTasksModel.getAppInfo().getIconTimes();
                    break;

            }


            adsTask.setCountType(info.getCountType());
            adsTask.setShowTime(info.getPrefer_time());
            adsTask.setWeight(info.getWeight());
            adsTask.loginState = getTasksModel.getLoginState();

            logger.debug("client:{} chosenTask detial:{}", getTasksModel.getModelIp(), adsTask.toString());

            ReportTaskStatusModel reportTaskModel = new ReportTaskStatusModel();

            reportTaskModel.setPhoneIndex(Integer.parseInt(getTasksModel.getPhoneIndex()));
            reportTaskModel.setTaskId(info.getId());
            reportTaskModel.setGetTaskState(0);

            //report_info.getTaskState 置 0
            int reportTblId = dao.addTaskReport(reportTaskModel);

            adsTask.setReportTblId(reportTblId);
            getTasksModel.addTasks(adsTask);

            //统计各任务的获取任务数
            /**     改为直接插入数据
             String appKey = getTasksModel.getApp_key();
             String channelId = getTasksModel.getChannelName();

             String key1 = CacheConstants.CACHE_APP_CHANNEL_GET_TASK_COUNT_ + adsTask.getTaskId() + CacheConstants.CACHE_KEY_SEPARATOR + appKey + CacheConstants.CACHE_KEY_SEPARATOR + channelId + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();
             String key2 = CacheConstants.CACHE_GET_TASK_COUNT_ + adsTask.getTaskId() + CacheConstants.CACHE_KEY_SEPARATOR + DateTimeUtils.getCurrentDay();

             cache.increment(key1,1);
             cache.increment(key2,1);
             */


        }


        if (getTasksModel.onWhatTaskList.size() == 0) {

            if (getTasksModel.tasks.size() == 0) {
                HashMap<String, Object> response = new HashMap<String, Object>();

                response.put("resultCode", "100");
                response.put("errorCode", "101");//无可执行任务
                HttpUtils.response(getTasksModel, response);
            } else {
                HashMap<String, Object> response = new HashMap<String, Object>();

                response.put("resultCode", "200");
                response.put("errorCode", getTasksModel.getTasks());


                HttpUtils.response(getTasksModel, response);
            }


        } else {
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

    public void setDao(GetTasksDao dao) {
        this.dao = dao;
    }
}
