package com.qt.constant;

public interface Constants {

	// response status
	public static final String OK = new String(new byte[] { (byte) 0x4f, (byte) 0x4b });
	public static final String NO = new String(new byte[] { (byte) 0x4e, (byte) 0x4f });

	// verification code
	public static final String START = new String(new byte[] { (byte) 0x55, (byte) 0x33 });
	public static final String END = new String(new byte[] { (byte) 0x0d, (byte) 0x0a });
}
