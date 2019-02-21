package com.nf147.prophet.util;

public class EmptyUtil {

    //判断一个字符串是否为空，为空返回真
    public static boolean checkUpStringIsNull(String val) {
        boolean result = false;

        if (val == null || val.isEmpty() || "".equals(val) || "null".equals(val.toLowerCase())) {
            result = true;
        }

        return result;
    }
}
