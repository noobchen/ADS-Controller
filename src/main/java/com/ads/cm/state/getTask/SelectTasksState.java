package com.ads.cm.state.getTask;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.GetTasksModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.weight.TaskWeightUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-28
 * Time: 上午11:54
 * 按照任务权重选取要执行的任务
 */
@Consumer("selectTasks")
public class SelectTasksState implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(GetAppInfosState.class);
    private Cache cache;

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        GetTasksModel getTasksModel = (GetTasksModel) event.getDomainMessage().getEventSource();
        logger.debug("client:{} onWhatTask:{} SelectTasksState", getTasksModel.getModelIp(), getTasksModel.onWhatTask);

        List<TaskInfos> tasks = getTasksModel.getInfoList();


        List<HashMap<String, Integer>> weightMaps = new ArrayList<HashMap<String, Integer>>();

        for (int i = 0; i < tasks.size(); i++) {
            HashMap<String, Integer> weightMap = new HashMap<String, Integer>();
            TaskInfos temp = tasks.get(i);

            weightMap.put("task_weights", temp.getWeight());
            weightMap.put("task_id", i);
            weightMaps.add(weightMap);
        }


//        if (getTasksModel.onWhatTask == 0 || getTasksModel.onWhatTask >= 4) {
//
//
//            if (getTasksModel.onWhatTaskList.size() == 0) {
//                if (getTasksModel.tasks.size() == 0) {
//                    logger.debug("getTasksModel.onWhatTaskList.size() == 0 !");
//
//                    HashMap<String, Object> response = new HashMap<String, Object>();
//
//                    response.put("resultCode", "100");
//                    response.put("errorCode", "101");//无可执行任务
//
//                    HttpUtils.response(getTasksModel, response);
//
//                    return;
//                } else {
//
//                    HashMap<String, Object> response = new HashMap<String, Object>();
//
//                    response.put("resultCode", "200");
//                    response.put("errorCode", getTasksModel.getTasks());
//
//                    HttpUtils.response(getTasksModel, response);
//
//                    return;
//                }
//            } else {
//                getTasksModel.onWhatTask = getTasksModel.onWhatTaskList.get(0);
//
//                logger.debug("getTasksModel.onWhatTask:{}", getTasksModel.onWhatTask);
//                getTasksModel.onWhatTaskList.remove(0);
//                logger.debug("getTasksModel.onWhatTaskList:{}", getTasksModel.onWhatTaskList.toString());
//
//                getTasksModel.getTasksInfoState();
//
//                return;
//            }
//
//        }

        int showTimes = 1;


        int indexs[] = new int[showTimes];


        List<TaskInfos> chosenTask = new ArrayList<TaskInfos>();

        for (int i = 0; i < indexs.length; i++) {
            indexs[i] = TaskWeightUtil.getWeightIndex(weightMaps);
            logger.debug("client:{} i :{} random by  weight:{} index:{}", new Object[]{getTasksModel.getModelIp(), i, weightMaps.get(indexs[i]).get("task_weights"), indexs[i]});
            TaskInfos taskInfo = tasks.get(indexs[i]);
            logger.debug("client:{} chosen taskInfo:{}", getTasksModel.getModelIp(), taskInfo);
            cache.setnx(CacheConstants.CACHE_TASKS_LEAVE_ + taskInfo.getId() + CacheConstants.CACHE_KEY_SEPARATOR, taskInfo.getTimes());
            chosenTask.add(taskInfo);
        }
        //
        getTasksModel.getInfoList().removeAll(getTasksModel.getInfoList());

        getTasksModel.setChosenTask(chosenTask);


        getTasksModel.getTasksDetial();
    }


    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
