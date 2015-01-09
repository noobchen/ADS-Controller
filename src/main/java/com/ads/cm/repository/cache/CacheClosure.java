package com.ads.cm.repository.cache;

import org.codehaus.jackson.type.TypeReference;

/**
 * Author: cyc
 * Date: 12-3-22
 * Time: 上午9:48
 * Description: to write something
 */
public interface CacheClosure {
    public String getKey();   //缓存键

    public Object getValue(); //缓存值

    public TypeReference getTypeReference();//类型

    public Integer getTime(); //时间,单位:秒,为空表示永久有效

    public boolean getIfNullSetDefaultValue(); //当值为空时，是否设置默认值标志

    public String getIfNullDefaultValue(); //当值为空时，设置的默认值
}
