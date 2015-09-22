package com.qt.util;

import com.qt.constant.Constants;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang.StringUtils;

public class DataParseUtil {

    public static String parseLength(byte data) {
        return String.valueOf((int) data);
    }

    public static String parseMotionState(byte data) {
        return String.valueOf((int) data);
    }

    public static int parsePulseState(byte data) {
        return (int) data;
    }

    public static float parseTemperature(byte[] data) {
        return Float.parseFloat(new String(data, CharsetUtil.US_ASCII));
    }

    public static String parseWarning(byte data) {
        return String.valueOf((int) data);
    }

    public static String parseBraceletId(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.valueOf(b));
        }
        return sb.toString();
    }

    public static String parseToString(byte[] data) {
        return new String(data, CharsetUtil.US_ASCII);
    }

    // verify the data packet is correct or not
    public static boolean doVerification(byte[] startArr, byte[] endArr) {
        String start = parseToString(startArr);
        String end = parseToString(endArr);
        if (StringUtils.equals(start, Constants.START) && StringUtils.equals(end, Constants.END)) {
            return true;
        }
        return false;
    }
}
