package com.cy.bear1.dbm;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数检查工具类<br/>
 * <li>空字符检查</li>
 */
public class PChkUtil {

    /**
     * 检查字符不定项，其中有一项为空，则判定检查为空；全部不为空时，则返回false（无空项）
     * @param params 字符参数数组
     * @return boolean
     */
    public static boolean checkNull(String ... params){
        boolean b = false;
        for(String p: params){
            b = isEmpty(p);// 是否为空对象、空字符串、空行、制表符
            if(b){
                break;// 有一项为空，则退出，并判定数组检查为含空项
            }
        }
        return b;
    }

    /**
     * check string is empty
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * check phone is valid
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        //Acceptable phone formats include:
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 获取当前年月日
     * 输出的值为System.out: 月：8 ##年：2018 ##日：20 ##时：11（其中输出的月份的值是实际月份-1）
     *
     * @param strFlag
     * @return
     */
    public static Integer getTime(String strFlag) {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间
        int reValue = 0;
        if (strFlag == "Y") {
            reValue = t.year;
        } else if (strFlag == "M") {
            reValue = t.month;
        } else if (strFlag == "D") {
            reValue = t.monthDay;
        } else if (strFlag == "H") {
            reValue = t.hour;
        }
        return reValue;
    }

    /**
     * @param date
     * @return
     */
    public static String format(Date date) {
        String str = "";
        SimpleDateFormat ymd = null;
        ymd = new SimpleDateFormat("yyyy-MM-dd");
        str = ymd.format(date);
        return str;
    }

    /**
     * 十六进制字符检查
     */
    public static boolean isHexString(String in){
        String reg = "^[0-9abcdefABCDEF]+";
        if(in.matches(reg)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Base64字符检查
     */
    public static boolean isBase64Str(String in){
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, in);
    }


}