package com.ads.cm.repository.load;

import com.ads.cm.annotation.Component;
import com.ads.cm.annotation.OnEvent;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.model.LoadManagerModel;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.cache.CacheClosure;
import com.ads.cm.repository.load.loadBean.LoadInfoBean;

import org.codehaus.jackson.type.TypeReference;

/**
 * Created by Administrator on 14-11-4.
 */
@Component
public class LoadManagerRepositoryImpl implements LoadManagerRepository {

    private LoadManagerDao loadManagerDao;
    private Cache cache;

    @Override
    @OnEvent("cheakUpdate")
    public LoadInfoBean cheakUpdate(final LoadManagerModel model) {
        LoadInfoBean loadInfo = (LoadInfoBean) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                return CacheConstants.CACHE_APP_CHANNEL_LOAD_INFO_ + model.getAppKey() + CacheConstants.CACHE_KEY_SEPARATOR + model.getChannelId() + CacheConstants.CACHE_KEY_SEPARATOR + model.getSdkVersion() + CacheConstants.CACHE_KEY_SEPARATOR;
            }

            @Override
            public Object getValue() {
                return loadManagerDao.cheakUpdate(model);  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<LoadInfoBean>() {
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

        return loadInfo;
    }

    public void setLoadManagerDao(LoadManagerDao loadManagerDao) {
        this.loadManagerDao = loadManagerDao;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
