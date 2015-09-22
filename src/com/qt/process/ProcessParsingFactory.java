package com.qt.process;

import com.qt.constant.Constants;
import com.qt.process.impl.StandardParsingImpl;
import com.qt.util.DataParseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by superleo on 22/9/15.
 */
public class ProcessParsingFactory {

    public static ProcessParsing createParsing(String value) {
        byte[] rawData = value.getBytes();
        String protocol = DataParseUtil.parseLength(rawData[3]);
        if (StringUtils.isEmpty(protocol)) {
            return null;
        }
        if (StringUtils.equals(protocol, Constants.STANDARD)) {
            return new StandardParsingImpl(value);
        }
        return null;
    }
}
