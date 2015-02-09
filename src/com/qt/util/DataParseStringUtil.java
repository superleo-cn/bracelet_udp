package com.qt.util;

import io.netty.util.CharsetUtil;

import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.qt.constant.Constants;
import com.qt.domain.Bracelet;

public class DataParseStringUtil {

	private static final Logger LOGGER = Logger.getLogger(DataParseStringUtil.class);

	private String rawData;

	private Bracelet bracelet = new Bracelet();

	public DataParseStringUtil() {

	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public DataParseStringUtil(String data) {
		this.rawData = data;
	}

	public Bracelet parseData() {
		try {
			String value = this.rawData;
			byte[] bytes = value.getBytes();
			byte[] startCode = Arrays.copyOfRange(bytes, 0, 2);
			String length = parseLength(bytes[2]);
			byte command = bytes[3];
			String motionState = parseMotionState(bytes[4]);
			int pulseState = parsePulseState(bytes[5]);
			float temperature = parseTemperature(Arrays.copyOfRange(bytes, 6, 10));
			String warning = parseWarning(bytes[10]);
			String braceletId = parseBraceletId(Arrays.copyOfRange(bytes, 11, 18));
			byte[] endCode = Arrays.copyOfRange(bytes, 18, 20);

			if (!doVerification(startCode, endCode)) {
				LOGGER.error("[DataParseByteUtil] -> [parseData] -> [invalid format : " + Hex.encodeHexString(bytes) + "]");
				return null;
			}

			bracelet.setStartCode(startCode);
			bracelet.setLength(length);
			bracelet.setCommand(command);
			bracelet.setMotionState(motionState);
			bracelet.setPulseState(pulseState);
			bracelet.setTemperature(temperature);
			bracelet.setWarning(warning);
			bracelet.setBraceletId(braceletId);
			bracelet.setEndCode(endCode);
			return bracelet;
		} catch (Exception e) {
			LOGGER.error("[DataParseByteUtil] -> [parseData] -> [exception]", e);
		}
		return null;
	}

	public Bracelet parseData(String data) {
		this.rawData = data;
		return parseData();
	}

	private String parseLength(byte data) {
		return String.valueOf((int) data);
	}

	private String parseMotionState(byte data) {
		return String.valueOf((int) data);
	}

	private int parsePulseState(byte data) {
		return (int) data;
	}

	private float parseTemperature(byte[] data) {
		return Float.parseFloat(new String(data, CharsetUtil.US_ASCII));
	}

	private String parseWarning(byte data) {
		return String.valueOf((int) data);
	}

	private String parseBraceletId(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.valueOf(b));
		}
		return sb.toString();
	}

	private String parseToString(byte[] data) {
		return new String(data, CharsetUtil.US_ASCII);
	}

	// verify the data packet is correct or not
	private boolean doVerification(byte[] startArr, byte[] endArr) {
		String start = parseToString(startArr);
		String end = parseToString(endArr);
		if (StringUtils.equals(start, Constants.START) && StringUtils.equals(end, Constants.END)) {
			return true;
		}
		return false;
	}
}
