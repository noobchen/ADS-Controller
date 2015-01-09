package com.ads.cm.repository.getTask;

import com.ads.cm.model.ReportTaskStatusModel;
import com.ads.cm.repository.getTask.taskBean.AdsTask;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-27
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
public interface GetTasksDao {

    public AppInfos getAppInfos(String appKey);

    public List<TaskInfos> getAllTasks(int type);

    public TaskInfos getTaskInfoByTaskId(int taskId);

    public AdsTask getTasksDetialById(int taskId);

    public void updateTaskStatus(int id);

    public void updateInstallStatus(int id);

    public int addTaskReport(ReportTaskStatusModel model);

    public void addMoreTaskReport(ReportTaskStatusModel model);

    public void updateTaskReport(int id);
}
