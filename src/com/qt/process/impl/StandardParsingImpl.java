package com.qt.process.impl;

import com.qt.domain.HealthData;
import com.qt.process.ProcessParsing;
import com.qt.util.DataParseUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class StandardParsingImpl implements ProcessParsing {

    private static final Logger LOGGER = Logger.getLogger(StandardParsingImpl.class);

    private String rawData;

    private HealthData healthData = new HealthData();

    public StandardParsingImpl(String rawData) {
        this.rawData = rawData;
    }

    public void process() {
        try {
            HealthData healthData = createData();
            HealthData.save(healthData);
        } catch (Exception e) {
            LOGGER.error("[StandardParsing] -> [process] -> [exception]", e);
        }
    }

    public HealthData createData() {
        String value = this.rawData;
        byte[] bytes = value.getBytes();
        byte[] startCode = Arrays.copyOfRange(bytes, 0, 2);
        String length = DataParseUtil.parseLength(bytes[2]);
        String protocol = DataParseUtil.parseLength(bytes[3]);
        byte command = bytes[4];
        String motionState = DataParseUtil.parseMotionState(bytes[5]);
        int pulseState = DataParseUtil.parsePulseState(bytes[6]);
        float temperature = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 7, 11));
        String warning = DataParseUtil.parseWarning(bytes[11]);
        int sbp = DataParseUtil.parsePulseState(bytes[12]);
        int dbp = DataParseUtil.parsePulseState(bytes[13]);
        String braceletId = DataParseUtil.parseBraceletId(Arrays.copyOfRange(bytes, 14, 21));
        float latitude = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 21, 31));
        float longitude = DataParseUtil.parseTemperature(Arrays.copyOfRange(bytes, 31, 41));
        byte[] endCode = Arrays.copyOfRange(bytes, 41, 43);

        if (!DataParseUtil.doVerification(startCode, endCode)) {
            LOGGER.error("[StandardParsing] -> [createBracelet] -> [invalid format : " + Hex.encodeHexString(bytes) + "]");
            return null;
        }

        healthData.setStartCode(startCode);
        healthData.setLength(length);
        healthData.setCommand(command);
        healthData.setMotionState(motionState);
        healthData.setPulseState(pulseState);
        healthData.setTemperature(temperature);
        healthData.setWarning(warning);
        healthData.setSbp(sbp);
        healthData.setDbp(dbp);
        healthData.setLatitude(latitude);
        healthData.setLongitude(longitude);
        healthData.setBraceletId(braceletId);
        healthData.setEndCode(endCode);
        return healthData;
    }

}
