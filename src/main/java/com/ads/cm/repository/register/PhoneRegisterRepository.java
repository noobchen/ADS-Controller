package com.ads.cm.repository.register;

import com.ads.cm.model.RegisterModel;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-25
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */
public interface PhoneRegisterRepository {

    public Long cheakAppInfoIsExit(RegisterModel model);

    public Long cheakUserInfoIsExit(RegisterModel model);

    public Long savePhoneInfo(RegisterModel model);


}
