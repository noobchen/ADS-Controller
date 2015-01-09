package com.ads.cm.state.getTask;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.SystemConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.GetTasksModel;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
@Consumer("getTasksInfoState")
public class GetTasksInfoState implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(GetAppInfosState.class);

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        GetTasksModel getTasksModel = (GetTasksModel) event.getDomainMessage().getEventSource();
        logger.debug("client:{} onWhatTask:{} GetTasksInfoState", getTasksModel.getModelIp(), getTasksModel.onWhatTask);


        List<TaskInfos> tasks = (List<TaskInfos>) getTasksModel.getAllTasks().getEventResult();


        if (tasks == null || tasks.size() == 0) {
            logger.debug("client:{} onWhatTask:{} tasks is null ! ", getTasksModel.getModelIp(), getTasksModel.onWhatTask);

            if (getTasksModel.onWhatTaskList.size() == 0) {
                if (getTasksModel.tasks.size() == 0) {
                    logger.debug("client:{} donot have task to execute !", getTasksModel.getModelIp());
                    LogInstance.getTaskLogger.debug("client:{} donot have task to execute !", getTasksModel.getModelIp());

                    HashMap<String, Object> response = new HashMap<String, Object>();

                    response.put("resultCode", "100");
                    response.put("errorCode", "101");//无可执行任务

                    HttpUtils.response(getTasksModel, response);

                    return;
                } else {

                    HashMap<String, Object> response = new HashMap<String, Object>();

                    response.put("resultCode", "200");
                    response.put("errorCode", getTasksModel.getTasks());

                    HttpUtils.response(getTasksModel, response);

                    return;
                }
            } else {
                getTasksModel.onWhatTask = getTasksModel.onWhatTaskList.get(0);

                logger.debug("client:{} select Task:{}", getTasksModel.getModelIp(), getTasksModel.onWhatTask);
                getTasksModel.onWhatTaskList.remove(0);
                logger.debug("client:{} onWhatTaskList remain:{}", getTasksModel.getModelIp(), getTasksModel.onWhatTaskList.toString());

                getTasksModel.getTasksInfoState();

                return;
            }
        }

        logger.debug("client:{} onWhatTask:{} getAllTasksInfoList:{}", new Object[]{getTasksModel.getModelIp(), getTasksModel.onWhatTask, tasks.toString()});


        getTasksModel.setInfoList(tasks);

        getTasksModel.cheakPhoneAttribute();


    }
}
