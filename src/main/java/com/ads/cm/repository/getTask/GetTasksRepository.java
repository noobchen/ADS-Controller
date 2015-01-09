package com.ads.cm.repository.getTask;

import com.ads.cm.repository.getTask.taskBean.AdsTask;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-27
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
public interface GetTasksRepository {
    public AppInfos getAppInfos(String appKey);

    public List<TaskInfos> getAllTasks(final Integer type);

    public TaskInfos getTaskInfoByTaskId(Integer taskId);

    public AdsTask getTasksDetialById(Integer taskId);


}
