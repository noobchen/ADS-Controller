package com.ads.cm.repository.register;

import com.ads.cm.model.RegisterModel;
import com.ads.cm.repository.area.areaBean.Area;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class PhoneRegisterDaoImpl extends SqlSessionTemplate implements PhoneRegisterDao {

    public PhoneRegisterDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Long cheakUserInfoIsExits(RegisterModel model) {
        Long id = (Long) selectOne("cheakIsExit", model);
        if (id != null) {
            return id;
        }

        return 0l;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long cheakAppChannelIsExits(RegisterModel model) {
        Long id = (Long) selectOne("cheakAppChannelIsExits", model);
        if (id != null) {
            return id;
        }

        return 0l;
    }

    @Override
    public Long cheakAppIsExits(RegisterModel model) {
        Long id = (Long) selectOne("cheakAppIsExits", model);
        if (id != null) {
            return id;
        }

        return 0l;
    }

    @Override
    public Long addAppInfo(RegisterModel model) {
        insert("addAppInfo", model);

        return model.getId();
    }

    @Override
    public Long savePhoneInfo(RegisterModel model) {
        Area area = model.area;

        if (area != null) {
            insert("savePhoneAttribute", area);
        }
        insert("savePhoneDetial", model);
        insert("savePhoneInfo", model);

        if (model.getId() != null) {
            return model.getId();
        } else {
            return 0l;
        }
    }

    @Override
    public void addAppChannelInfo(RegisterModel model) {
        insert("addAppChannelInfo", model);
    }

    @Override
    public void getAppId(RegisterModel model) {
        Long id = (Long) selectOne("getAppId", model);
        model.setAppId(id);
    }
}
