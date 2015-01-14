package com.qt.domain;

import org.junit.Test;

public class BraceletTest {
	@Test
	public void testSave() {
		Bracelet bracelet = new Bracelet();
		bracelet.setBraceletId("1");
		bracelet.setMotionState("0");
		bracelet.setPulseState(100);
		bracelet.setTemperature(36.8f);
		Bracelet.save(bracelet);
	}
	
}
