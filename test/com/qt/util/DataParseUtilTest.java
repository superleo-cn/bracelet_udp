package com.qt.util;

import org.junit.Test;

public class DataParseUtilTest {

    private DataParseStringUtil stringUtil = new DataParseStringUtil();

    @Test
    public void testStringParseData() {
        String rawData = new String(new byte[]{(byte) 0x55, (byte) 0x33, (byte) 0x10, (byte) 0x01, (byte) 0x01, (byte) 0x3C, (byte) 0x33, (byte) 0x36, (byte) 0x2E, (byte) 0x35,
                (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x0D, (byte) 0x0A});

        String rawData2 = new String(new byte[]{(byte) 0x56, (byte) 0x33, (byte) 0x10, (byte) 0x01, (byte) 0x01, (byte) 0x3C, (byte) 0x33, (byte) 0x36, (byte) 0x2E, (byte) 0x35,
                (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x0D, (byte) 0x0A});

        String rawData3 = new String(new byte[]{(byte) 0x55, (byte) 0x33, (byte) 0x10, (byte) 0x01, (byte) 0x01, (byte) 0x3C, (byte) 0x33, (byte) 0x36, (byte) 0x2E, (byte) 0x35,
                (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x0E, (byte) 0x0A});


    }

}
