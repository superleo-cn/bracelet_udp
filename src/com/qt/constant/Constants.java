package com.qt.constant;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Constants {

	private static final Logger LOGGER = Logger.getLogger(Constants.class);

	public static final Properties prop = new Properties();

	static {
		InputStream inputStream = Constants.class.getClassLoader().getResourceAsStream("config.properties");
		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (Exception e) {
				LOGGER.error("[Constants] -> [load property file failed]", e);
			}
		}
	}

	// response status
	public static final String OK = new String(new byte[] { (byte) 0x4f, (byte) 0x4b });
	public static final String NO = new String(new byte[] { (byte) 0x4e, (byte) 0x4f });

	// verification code
	public static final String START = new String(new byte[] { (byte) 0x55, (byte) 0x33 });
	public static final String END = new String(new byte[] { (byte) 0x0d, (byte) 0x0a });

	// server setting
	public static final String SERVER_IP = prop.getProperty("server.ip");
	public static final int SERVER_PORT = Integer.parseInt(prop.getProperty("server.port"));

}
