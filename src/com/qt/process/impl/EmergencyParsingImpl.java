package com.qt.process.impl;

import com.qt.domain.EmergencyData;
import com.qt.process.ProcessParsing;
import com.qt.util.DataParseUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class EmergencyParsingImpl implements ProcessParsing {

    private static final Logger LOGGER = Logger.getLogger(EmergencyParsingImpl.class);

    private String rawData;

    private EmergencyData emergencyData = new EmergencyData();

    public EmergencyParsingImpl(String rawData) {
        this.rawData = rawData;
    }

    public void process() {
        try {
            EmergencyData emergencyData = createData();
            EmergencyData.save(emergencyData);
        } catch (Exception e) {
            LOGGER.error("[EmergencyParsingImpl] -> [process] -> [exception]", e);
        }
    }

    public EmergencyData createData() {
        String value = this.rawData;
        byte[] bytes = value.getBytes();
        byte[] startCode = Arrays.copyOfRange(bytes, 0, 2);
        String length = DataParseUtil.parseLength(bytes[2]);
        String protocol = DataParseUtil.parseLength(bytes[3]);
        byte command = bytes[4];
        String braceletId = DataParseUtil.parseBraceletId(Arrays.copyOfRange(bytes, 5, 12));
        String status = DataParseUtil.parseMotionState(bytes[12]);
        float gyroscope = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 13, 17));
        float acceleration = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 17, 21));
        float time = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 21, 25));
        float position = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 25, 29));
        float latitude = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 29, 39));
        float longitude = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 39, 49));
        byte[] endCode = Arrays.copyOfRange(bytes, 49, 51);

        if (!DataParseUtil.doVerification(startCode, endCode)) {
            LOGGER.error("[EmergencyParsingImpl] -> [createBracelet] -> [invalid format : " + Hex.encodeHexString(bytes) + "]");
            return null;
        }

        emergencyData.setStartCode(startCode);
        emergencyData.setLength(length);
        emergencyData.setCommand(command);
        emergencyData.setStatus(status);
        emergencyData.setGyroscope(gyroscope);
        emergencyData.setAcceleration(acceleration);
        emergencyData.setTime(time);
        emergencyData.setPosition(position);
        emergencyData.setLatitude(latitude);
        emergencyData.setLongitude(longitude);
        emergencyData.setBraceletId(braceletId);
        return emergencyData;
    }

}
