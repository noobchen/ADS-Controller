package com.ads.cm.repository.load;

import com.ads.cm.model.LoadManagerModel;
import com.ads.cm.repository.load.loadBean.LoadInfoBean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * Created by Administrator on 14-11-4.
 */

public class LoadManagerDaoImpl extends SqlSessionTemplate implements LoadManagerDao {

    public LoadManagerDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public LoadInfoBean cheakUpdate(LoadManagerModel model) {
        return (LoadInfoBean) selectOne("cheakUpdate", model);
    }
}
