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

        String imei = model.getImei();

        if (blackList.contains(imei)) {
            return false;
        }

        return true;
    }
}
