package com.ads.cm.repository.getTask;

import com.ads.cm.constant.SystemConstants;
import com.ads.cm.repository.getTask.GetTasksRepository;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.cache.CacheClosure;
import com.ads.cm.repository.getTask.taskBean.AdsTask;
import com.ads.cm.repository.getTask.taskBean.AppInfos;
import com.ads.cm.annotation.Component;
import com.ads.cm.annotation.OnEvent;
import com.ads.cm.repository.getTask.taskBean.TaskInfos;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-27
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GetTasksRepositoryImpl implements GetTasksRepository {
    private final Logger logger = LoggerFactory.getLogger(GetTasksRepositoryImpl.class);

    private GetTasksDao getTasksDao;
    private Cache cache;


    @Override
    @OnEvent("getAppInfos")
    public AppInfos getAppInfos(final String appKey) {
        String[] keyAndChannelId = appKey.split(",");

        final String key = keyAndChannelId[0];
        final String channelId = keyAndChannelId[1];



        AppInfos appInfos = (AppInfos) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                if (channelId.equals("undefined")) {
                    return CacheConstants.CACHE_APPKEY_ + key;  //兼容之前未分渠道的版本
                } else {
                    return CacheConstants.CACHE_APPKEY_ + key + CacheConstants.CACHE_KEY_SEPARATOR + channelId;
                }
            }

            @Override
            public Object getValue() {
                return getTasksDao.getAppInfos(appKey);  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<AppInfos>() {
                };
            }

            @Override
            public Integer getTime() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean getIfNullSetDefaultValue() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getIfNullDefaultValue() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        return appInfos;
    }


    @Override
    @OnEvent("getAllTasks")
    public List<TaskInfos> getAllTasks(final Integer type) {

        List<TaskInfos> tasks = (List<TaskInfos>) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                switch (type) {
                    case 1:
                        return CacheConstants.CACHE_TASKS_PUSH_;


                    case 2:
                        return CacheConstants.CACHE_TASKS_BANNER_;


                    case 3:
                        return CacheConstants.CACHE_TASKS_ICON_;


                    default:
                        return null;
                }
            }

            @Override
            public Object getValue() {
                return getTasksDao.getAllTasks(type);  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<List<TaskInfos>>() {
                };
            }

            @Override
            public Integer getTime() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean getIfNullSetDefaultValue() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getIfNullDefaultValue() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });


        return tasks;

    }

    @Override
    @OnEvent("getTaskInfoByTaskId")
    public TaskInfos getTaskInfoByTaskId(final Integer taskId) {

        TaskInfos task = (TaskInfos) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                return CacheConstants.CACHE_TASKS_INFO_ + taskId;
            }

            @Override
            public Object getValue() {
                return getTasksDao.getTaskInfoByTaskId(taskId);  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<TaskInfos>() {
                };
            }

            @Override
            public Integer getTime() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean getIfNullSetDefaultValue() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getIfNullDefaultValue() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });


        return task;

    }

    @Override
    @OnEvent("getTasksDetialById")
    public AdsTask getTasksDetialById(final Integer taskId) {

        AdsTask tasks = (AdsTask) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                return CacheConstants.CACHE_TASKS_DETIAL_INFO_ + String.valueOf(taskId);
            }

            @Override
            public Object getValue() {
                return getTasksDao.getTasksDetialById(taskId);  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<AdsTask>() {
                };
            }

            @Override
            public Integer getTime() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean getIfNullSetDefaultValue() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getIfNullDefaultValue() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });


        return tasks;

    }


    public void setGetTasksDao(GetTasksDao getTasksDao) {
        this.getTasksDao = getTasksDao;
    }


    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
