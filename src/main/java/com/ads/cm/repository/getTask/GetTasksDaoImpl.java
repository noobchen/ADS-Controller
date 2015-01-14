package com.ads.cm.repository.getTask;

import com.ads.cm.constant.ErrorCodeConstant;
import com.ads.cm.model.ReportTaskStatusModel;
import com.ads.cm.repository.getTask.taskBean.AdsTask;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-27
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class GetTasksDaoImpl extends SqlSessionTemplate implements GetTasksDao {

    public GetTasksDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public AppInfos getAppInfos(String appKey) {
        AppInfos infos = null;

        String[] keyAndChannel = appKey.split(",");

        String channelId = keyAndChannel[1];
        String key = keyAndChannel[0];

        HashMap<String, String> queryMap = new HashMap<String, String>();

        queryMap.put("channelId", channelId);
        queryMap.put("key", key);

        infos = (AppInfos) selectOne("getAppInfoV2", queryMap);


        return infos;

    }


    @Override
    public List<TaskInfos> getAllTasks(int type) {
        List<TaskInfos> list = (List<TaskInfos>) selectList("getAllTasks", type);

        for (TaskInfos temp : list) {
            temp.setType(type);
        }

        if (list == null || list.size() == 0) {
            return null;
        }

        return list;
    }

    @Override
    public TaskInfos getTaskInfoByTaskId(int taskId) {
        return (TaskInfos) selectOne("getTaskInfoByTaskId", taskId);
    }

    @Override
    public AdsTask getTasksDetialById(int taskId) {

        return (AdsTask) selectOne("getTaskDetial", taskId);

    }

    @Override
    public void updateTaskStatus(int id) {
        update("updateTaskStatus", id);
    }

    @Override
    public void updateInstallStatus(int id) {
        update("updateInstallStatus", id);
    }

    @Override
    public int addTaskReport(ReportTaskStatusModel model) {

        insert("addTaskReport", model);
        return model.getId();
    }

    @Override
    public void addMoreTaskReport(ReportTaskStatusModel model) {
        if (model.getGetTaskState() == null) {
            model.setGetTaskState(1);
        }

        update("addMoreTaskReport", model);
    }

    @Override
    public void updateTaskReport(int id) {
        update("updateTaskReport", id);
    }
}
