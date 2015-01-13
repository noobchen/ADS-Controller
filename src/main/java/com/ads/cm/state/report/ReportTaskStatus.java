package com.ads.cm.state.report;

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
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午3:38
 * 插屏结果反馈
 */
@Consumer("reportTaskStatusState")
public class ReportTaskStatus implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(ReportTaskStatus.class);

    private GetTasksDao dao;
    private Cache cache;

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        ReportTaskStatusModel reportTasksModel = (ReportTaskStatusModel) event.getDomainMessage().getEventSource();
        logger.debug("client:{} reportTasksModel:{}", reportTasksModel.getModelIp(), reportTasksModel.toString());
        LogInstance.reportTaskLogger.debug("client:{} reportTasksModel:{}", reportTasksModel.getModelIp(), reportTasksModel.toString());

        String zlbTaskKey = CacheConstants.ZLB_GETTASK_KEY + reportTasksModel.getReportTblId();

        if (cache.exists(zlbTaskKey)) {
            reportTasksModel.transmitReportTaskState();
            return;
        }

        TaskInfos task = (TaskInfos) reportTasksModel.getTaskInfoByTaskId().getEventResult();


        int countType = task.getCountType();               //统计类型
        int type = task.getType();                          //任务类型


        long leave = 9999;
        if (countType == SystemConstants.COUNT_BY_SHOW) {                                   //按展示统计
            leave = cache.decrease(CacheConstants.CACHE_TASKS_LEAVE_ + reportTasksModel.getTaskId() + CacheConstants.CACHE_KEY_SEPARATOR);
        } else {                                                                             //按下载统计
            if (reportTasksModel.getDownState() == 1) {
                leave = cache.decrease(CacheConstants.CACHE_TASKS_LEAVE_ + reportTasksModel.getTaskId() + CacheConstants.CACHE_KEY_SEPARATOR);
            }
        }


        if (leave <= 0) {                                                                // 任务完成，删除相关key，更新任务状态
            dao.updateTaskStatus(reportTasksModel.getTaskId());


            switch (type) {
                case SystemConstants.ABILITY_PUSH:
                    cache.del(CacheConstants.CACHE_TASKS_PUSH_);
                    break;
                case SystemConstants.ABILITY_BANNER:
                    cache.del(CacheConstants.CACHE_TASKS_BANNER_);
                    break;
                case SystemConstants.ABILITY_ICON:
                    cache.del(CacheConstants.CACHE_TASKS_ICON_);
                    break;

            }
        }


        dao.addMoreTaskReport(reportTasksModel);


        HashMap<String, Object> reponse = new HashMap<String, Object>();

        reponse.put("linkId", reportTasksModel.getReportTblId() + "");
        reponse.put("resultCode", "200");

        HttpUtils.response(reportTasksModel, reponse);
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void setDao(GetTasksDao dao) {
        this.dao = dao;
    }
}
