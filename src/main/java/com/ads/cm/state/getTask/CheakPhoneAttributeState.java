package com.ads.cm.state.getTask;

import com.ads.cm.annotation.Consumer;
import com.ads.cm.constant.ProviderConstants;
import com.ads.cm.domain.message.DomainEventHandler;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.GetTasksModel;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import com.ads.cm.strategy.area.AreaStrategy;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;
import com.ads.cm.util.phone.PhoneUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ads.cm.repository.area.areaBean.Area;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-27
 * Time: 下午4:20
 * 过滤不符合运营商，省份，时间，已经下载过的任务
 */
@Consumer("cheakPhoneAttribute")
public class CheakPhoneAttributeState implements DomainEventHandler {
    private List<AreaStrategy> areaStrategies;

    private final Logger logger = LoggerFactory.getLogger(GetAppInfosState.class);

    @Override
    public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        GetTasksModel getTasksModel = (GetTasksModel) event.getDomainMessage().getEventSource();
        logger.debug("client:{} onWhatTask:{} CheakPhoneAttributeState", getTasksModel.getModelIp(), getTasksModel.onWhatTask);

        Area area = (Area) getTasksModel.getPhoneAttribute().getEventResult();


        if (area == null) {

            Area conditionArea = new Area();
            conditionArea.setPhone(getTasksModel.getPhoneNum());
            conditionArea.setSmsp("");
            conditionArea.setIp(getTasksModel.getIp());
            conditionArea.setImsi(getTasksModel.getImsi());


            for (AreaStrategy areaStrategy : areaStrategies) {
                area = areaStrategy.getArea(conditionArea);

                if (null != area) {
                    area.setImsi(getTasksModel.getImsi());
                    getTasksModel.savePhoneAttribute(area);
                    logger.debug("client:{} find area:{} by stratrgy", getTasksModel.getModelIp(), area.toString());
                    break;
                }
            }

            if (null == area) {
                //如果没有判断出地区和运营商，运营商则从IMSI中判断出,地区采用全国
                area = new Area();

                area.setProvince_id(ProviderConstants.CHINA_ALL_PROVINCE);
                area.setCity_id(ProviderConstants.CHINA_ALL_CITY);
                area.setProvider_id(PhoneUtils.getProviderId(conditionArea.getImsi()));
            }
        }


        int providerId = area.getProvider_id();

        int provinceId = area.getProvince_id();

        int cityId = area.getCity_id();


        List<TaskInfos> taskInfos = getTasksModel.getInfoList();           //所有任务
        List<TaskInfos> delList = new ArrayList<TaskInfos>();                      //不符合运营商，时间，省份要删除的任务

        for (TaskInfos temp : taskInfos) {

            String provider = temp.getPrefer_provider();
            String province = temp.getPrefer_province();
            String city = temp.getPrefer_city();
            String taskId = temp.getId().toString();
            String preferTime = temp.getPrefer_time();

            if (!provider.contains(String.valueOf(ProviderConstants.CHINA_ALL_PROVIDER))) {
                List<String> preferProviders = temp.getPreferList(temp.getPrefer_provider());

                if (!preferProviders.contains(String.valueOf(providerId))) {
                    logger.debug("client:{} task:{} do not contains providerId:{} remove it", new Object[]{getTasksModel.getModelIp(), temp.toString(), providerId});

                    delList.add(temp);
                    continue;
                }

            }

            if (!province.contains(String.valueOf(ProviderConstants.CHINA_ALL_PROVINCE))) {
                List<String> preferProvinces = temp.getPreferList(temp.getPrefer_province());

                if (!preferProvinces.contains(String.valueOf(provinceId)) && provinceId != ProviderConstants.CHINA_ALL_PROVINCE) {
                    logger.debug("client:{} task:{} do not contains provinceId:{} remove it", new Object[]{getTasksModel.getModelIp(), temp.toString(), provinceId});

                    delList.add(temp);
                    continue;
                }

            }

            if (!city.contains(String.valueOf(ProviderConstants.CHINA_ALL_CITY))) {
                List<String> preferCitys = temp.getPreferList(temp.getPrefer_city());

                if (!preferCitys.contains(String.valueOf(cityId)) && cityId != ProviderConstants.CHINA_ALL_CITY) {
                    logger.debug("client:{} task:{} do not contains cityId:{} remove it", new Object[]{getTasksModel.getModelIp(), temp.toString(), cityId});

                    delList.add(temp);
                    continue;
                }

            }

            if (!preferTime.contains(String.valueOf(ProviderConstants.SHOW_WHOLE_DAY))) {
                List<String> preferTimes = temp.getPreferList(temp.getPrefer_time());
                int now = getNowTime();
                if (!preferTimes.contains(String.valueOf(now))) {
                    logger.debug("client:{} task:{} do not contains now:{} remove it", new Object[]{getTasksModel.getModelIp(), temp.toString(), now});

                    delList.add(temp);
                    continue;
                }

            }

            if (getTasksModel.getHaveDownTaskIdList().contains(taskId)) {
                logger.debug("client:{} task:{} have downLoad So remove it", getTasksModel.getModelIp(), temp.toString());

                delList.add(temp);
                continue;
            }


        }

        taskInfos.removeAll(delList);                   //删除不符合条件的任务

        if (taskInfos.size() == 0) {
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
            }

            return;

        }


        getTasksModel.selectTasks();

    }

    public void setAreaStrategies(List<AreaStrategy> areaStrategies) {
        this.areaStrategies = areaStrategies;
    }

    public Integer getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return Integer.parseInt(formatter.format(curDate));
    }
}
