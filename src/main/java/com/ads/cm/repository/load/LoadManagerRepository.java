package com.ads.cm.repository.load;

import com.ads.cm.model.LoadManagerModel;
import com.ads.cm.repository.load.loadBean.LoadInfoBean;


/**
 * Created by Administrator on 14-11-4.
 */
public interface LoadManagerRepository {

    public LoadInfoBean cheakUpdate(LoadManagerModel model);
}
