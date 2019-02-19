package com.cy.common.bus.equip;

import android.os.Build;
import android.util.Log;

import com.cy.common.bus.L;

/**
 * 设备信息使用类
 */
public class EquipmentUtil {


    public static void getEquipmentInfo(){

        Log.i(L.BUS, "测试获取设备信息");

        String mfacName = Build.MANUFACTURER;
        String cellModel = Build.MODEL;
        String equName = Build.DEVICE;
        String devSeril = Build.SERIAL;
        int sdkVer = Build.VERSION.SDK_INT;
        String andVer = Build.VERSION.RELEASE;

        Log.i(L.BUS, String.format("参数: 厂商：%s 型号：%s 设备名：%s 设备序列号：%s SDK版本：%s 操作系统版本： %s", mfacName, cellModel, equName, devSeril, sdkVer, andVer ));

    }


}