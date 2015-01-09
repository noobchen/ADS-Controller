package com.ads.cm.repository.register;

import com.ads.cm.model.RegisterModel;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public interface PhoneRegisterDao {
    public Long cheakUserInfoIsExits(RegisterModel model);

    public Long cheakAppChannelIsExits(RegisterModel model);

    public Long savePhoneInfo(RegisterModel model);

    public void addAppChannelInfo(RegisterModel model);
}
