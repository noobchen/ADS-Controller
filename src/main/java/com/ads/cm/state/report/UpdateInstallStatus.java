package com.ads.cm.state.report;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.UpdateTaskStatusModel;
import com.ads.cm.repository.getTask.GetTasksDao;
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
 * 插屏结果更新
 */
@Consumer("updateInstallStatus")
public class UpdateInstallStatus implements DomainEventHandler {
    private final Logger logger = LoggerFactory.getLogger(UpdateInstallStatus.class);

    private GetTasksDao dao;


    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        UpdateTaskStatusModel updateTaskStatusModel = (UpdateTaskStatusModel) event.getDomainMessage().getEventSource();
        logger.debug("client:{} reportTasksModel:{}",updateTaskStatusModel.getModelIp(), updateTaskStatusModel.toString());
        LogInstance.updateTaskLogger.debug("client:{} reportTasksModel:{}",updateTaskStatusModel.getModelIp(), updateTaskStatusModel.toString());
        dao.updateInstallStatus(updateTaskStatusModel.getLinkId());


        HashMap<String, Object> reponse = new HashMap<String, Object>();


        reponse.put("resultCode", "200");

        HttpUtils.response(updateTaskStatusModel, reponse);
    }


    public void setDao(GetTasksDao dao) {
        this.dao = dao;
    }
}
