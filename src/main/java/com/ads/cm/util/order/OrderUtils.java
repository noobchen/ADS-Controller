package com.ads.cm.util.order;

import com.ads.cm.util.datetime.DateTimeUtils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: cyc
 * Date: 12-3-20
 * Time: 上午11:25
 * Description: to write something
 */
public class OrderUtils {
    private static AtomicLong xjAtomicLong = new AtomicLong(0);
    private static AtomicLong netGameAtomicLong = new AtomicLong(0);
    private static AtomicLong atomicLong = new AtomicLong(0);
    private static AtomicLong thirdLong = new AtomicLong(0);
    private static AtomicLong phonefeeLong = new AtomicLong(0);

    private static Random random = new Random();

    public static String getPayOrderId() {
        return UUID.randomUUID().toString();
    }

    public static String getThirdPayOrderId(String serverNo) {
        StringBuilder sb = new StringBuilder();
        sb.append(serverNo);
        sb.append(DateTimeUtils.getOrderTime());
        sb.append(getThirdPayNextInteger());
        return sb.toString();
    }

    public static String getPhoneFeeOrderid(String serverNo) {
        StringBuilder sb = new StringBuilder();
        sb.append(DateTimeUtils.getOrderTime());
        sb.append(serverNo);
        sb.append(getPhoneFeeNextInteger());
        return sb.toString();
    }

    public static String getNetGamePayOrderId(String serverNo, String fromId, String productId) {
        StringBuilder sb = new StringBuilder();
        sb.append(fromId);
        sb.append(productId);
        sb.append(DateTimeUtils.getNetGamePayPayOrderRequestTime());
        sb.append(serverNo);
        sb.append(getNetGameNextInteger());
        return sb.toString();

    }

    public static String getXJPayOrderId(String serverNo) {
        StringBuilder sb = new StringBuilder();
        sb.append(serverNo);
        sb.append(DateTimeUtils.getXJPayOrderRequestTime());
        sb.append(getXJPayNextInteger());
        return sb.toString();
    }

    private static String getPhoneFeeNextInteger() {
        long i = phonefeeLong.getAndIncrement();

        String str = "00000" + i;
        return str.substring(str.length() - 6);
    }

    private static String getNetGameNextInteger() {
        long i = netGameAtomicLong.getAndIncrement();

        String str = "000" + i;
        return str.substring(str.length() - 4);
    }

    private static String getXJPayNextInteger() {
        long i = xjAtomicLong.getAndIncrement();

        String str = "00000" + i;
        return str.substring(str.length() - 6);
    }

    private static String getThirdPayNextInteger() {
        long i = thirdLong.getAndIncrement();

        String str = "00000" + i;
        return str.substring(str.length() - 6);
    }

    public static String getOrderId(int digit) {
        long v = atomicLong.getAndIncrement();
        String s = String.valueOf(v);
        int length = s.length();
        if (s.length() < digit) {
            for (int i = 0; i <= (digit - length) + 1; i++) {
                s = "0" + s;
            }
        }
        return s.substring(s.length() - digit);
    }

    public static String getRandom(int len) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buf.append(random.nextInt(10));//取三个随机数追加到StringBuffer
        }
        return buf.toString();
    }

//    public static String toHexString(String s)
//    {
//        String str="";
//        for (int i=0;i<s.length();i++)
//        {
//            int ch = (int)s.charAt(i);
//            String s4 = Integer.toHexString(ch);
//            str = str + s4;
//        }
//        return str;
//    }

    public static String getAppFeeCode(String feePort, String feeCode, String index) {
        int feeStyle = 0;  //1是普通短信     2是移动基地网游

        if (feePort.equals("106588992")) {
            feeStyle = 2;
        } else {
            feeStyle = 1;
        }

        index = Long.toHexString(Long.valueOf(index));

        switch (feeStyle) {
            case 1:
                feeCode = feeCode + index;
                break;
            case 2:
                int countX = countX(feeCode);
//                System.out.println("coutX:"+countX);
                if (index.length() > countX) {

                } else {
                    int gap = countX - index.length();
//                    System.out.println("gap:"+gap);
                    String subFeeCode = feeCode.substring(0, 64);
//                    System.out.println("subFeeCode:"+subFeeCode);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(subFeeCode);

                    for (int temp = 0; temp < gap; temp++) {
                        stringBuilder.append("0");
                    }

                    stringBuilder.append(index);


                    feeCode = stringBuilder.toString();
                    System.out.println("feeCode:"+feeCode);

                }
                break;
        }


        return feeCode;


    }


    public static long getPayOrderIndex(int feeType, String feeCode, String changedFeeCode) {
        String payOrderIndex = "";
        if (feeType == 1) {   //普通短信
            int index = changedFeeCode.indexOf(feeCode);

            payOrderIndex = changedFeeCode.substring(index+feeCode.length());

        } else {
            int index = feeCode.indexOf("X");
            int count = countX(feeCode);

            payOrderIndex = changedFeeCode.substring(index , index + count );

        }

        return Long.parseLong(payOrderIndex, 16);

    }


    private static int countX(String feeCode) {
//        boolean hasX = true;
        int countX = 0;
        int index = feeCode.indexOf("X");

        if (index != 0) {
            feeCode = feeCode.substring(index);
            for (int i = 0;i<feeCode.length();i++){

                if (feeCode.charAt(countX) == 'X'){
                    countX++;
                }
            }
//            while (hasX) {
//                countX++;
//                feeCode = feeCode.substring(index);
//                System.out.println("feeCode:{}"+feeCode);
//                if (feeCode.indexOf("X") == 0) {
//                    hasX = false;
//                }
//
//            }

            return countX;
        } else {
            return 0;
        }


    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandom(2));
        }
    }
}
