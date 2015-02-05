package com.ads.cm.util.Transmit;

import com.ads.cm.model.GetTasksModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/12.
 */
public class TransmitUtils {

    public static boolean cheakIsTransmit(GetTasksModel model) {
        ArrayList<String> blackList = new ArrayList<String>();
        blackList.add("A0000033328B82");
        blackList.add("866335011909713");
        blackList.add("864501024228894");
        blackList.add("865453020188145");
        blackList.add("861022008122956");        //qi
        blackList.add("865267020422332");

        String imei = model.getImei();

        if (blackList.contains(imei) || model.hasTransmitEd) {
            return false;
        }

        if (model.getChannelName().equals("dtswz-channel36") || model.getChannelName().equals("dtswz-channel21")) {
            return true;
        }

        return false;
    }
}
