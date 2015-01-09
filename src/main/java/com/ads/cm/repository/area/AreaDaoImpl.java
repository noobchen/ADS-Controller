package com.ads.cm.repository.area;

import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.repository.area.areaBean.IP;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * Author: cyc
 * Date: 12-3-20
 * Time: 下午11:16
 * Description: to write something
 */
public class AreaDaoImpl extends SqlSessionTemplate implements AreaDao {
    public AreaDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Area getPhoneAttribute(String imsi) {

        return (Area) selectOne("getPhoneAttribute", imsi);
    }

    @Override
    public void savePhoneAttribute(Area area) {

        insert("savePhoneAttribute", area);
    }

    @Override
    public Area getAreaByPhone(String phonePrefix) {
        return (Area) selectOne("getAreaByPhone", phonePrefix);
    }

    @Override
    public Area getAreaBySmsp(String smsp) {
        return (Area) selectOne("getAreaBySmsp", smsp);
    }

    @Override
    public Area getAreaByIp(Long ip) {
        return (Area) selectOne("getAreaByIp", ip);
    }

    @Override
    public List<IP> getAllIp() {
        return (List<IP>) selectList("getAllIp");
    }


}
