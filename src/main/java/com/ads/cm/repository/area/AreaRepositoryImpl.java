package com.ads.cm.repository.area;

import com.ads.cm.annotation.Component;
import com.ads.cm.annotation.OnEvent;
import com.ads.cm.constant.CacheConstants;
import com.ads.cm.repository.area.areaBean.Area;
import com.ads.cm.repository.area.areaBean.IP;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.cache.CacheClosure;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-28
 * Time: 上午9:17
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AreaRepositoryImpl implements AreaRepository {
    private AreaDao areaDao;
    private Cache cache;
    private List<IP> ips = new ArrayList<IP>();

    @PostConstruct
    public void initial() {
        initialIp();
    }

    @Override
    @OnEvent("getPhoneAttribute")
    public Area getPhoneAttribute(final String imsi) {
        Area area = (Area) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                return CacheConstants.AREA_IMSI_PREFIX + imsi;
            }

            @Override
            public Object getValue() {
                return areaDao.getPhoneAttribute(imsi);
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<Area>() {
                };
            }

            @Override
            public Integer getTime() {
                return null;
            }

            @Override
            public boolean getIfNullSetDefaultValue() {
                return false;
            }

            @Override
            public String getIfNullDefaultValue() {
                return null;
            }
        });


        return area;

    }

    @Override
    @OnEvent("savePhoneAttribute")
    public void savePhoneAttribute(Area area) {

           areaDao.savePhoneAttribute(area);
    }

    @Override
    @OnEvent("getAreaByPhone")
    public Area getAreaByPhone(final String phone) {
        Area area = (Area) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                return CacheConstants.AREA_PHONE_PREFIX + phone;
            }

            @Override
            public Object getValue() {
                return areaDao.getAreaByPhone(phone);
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<Area>() {
                };
            }

            @Override
            public Integer getTime() {
                return null;
            }

            @Override
            public boolean getIfNullSetDefaultValue() {
                return false;
            }

            @Override
            public String getIfNullDefaultValue() {
                return null;
            }
        });

        return area;
    }

    @Override
    @OnEvent("getAreaBySmsp")
    public Area getAreaBySmsp(final String smsp) {


        Area area = (Area) cache.getAndSet(new CacheClosure() {
            @Override
            public String getKey() {
                return CacheConstants.AREA_PHONE_PREFIX + smsp;
            }

            @Override
            public Object getValue() {
                return areaDao.getAreaBySmsp(smsp);
            }

            @Override
            public TypeReference getTypeReference() {
                return new TypeReference<Area>() {
                };
            }

            @Override
            public Integer getTime() {
                return null;
            }

            @Override
            public boolean getIfNullSetDefaultValue() {
                return false;
            }

            @Override
            public String getIfNullDefaultValue() {
                return null;
            }
        });

        return area;
    }


    public void initialIp() {
        List<IP> ips = areaDao.getAllIp();
        this.ips = ips;
    }

    @Override
    @OnEvent("getAreaByIp")
    public Area getAreaByIp(final Long ip) {
        if (null == ips && ips.size() == 0) {
            return null;
        }
        try {
            //二分法查找ip库
            IP areaIP = searchIP(ip);
            if (null == areaIP) {
                return null;
            } else {
                Area area = new Area();
                area.setProvider_id(areaIP.getProvider_id());
                area.setProvince_id(areaIP.getProvince_id());
                area.setCity_id(areaIP.getCity_id());
                return area;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private IP searchIP(Long ip) {
        int low = 0;
        int high = ips.size() - 1;
        while (low <= high) {
            int mid = low + ((high - low) / 2);
            IP area = ips.get(mid);
            if (area.getIp_start_num() <= ip && area.getIp_end_num() >= ip) {
                //已经找到
                return area;
            } else if (area.getIp_start_num() > ip) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return null;
    }


    public void setAreaDao(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
