package com.qt.domain;

import com.leo.MyUtil;
import org.junit.Test;

import java.util.Random;

public class HealthDataTest {

    private static final Random random = new Random();

    @Test
    public void testSave() {
        HealthData healthData = new HealthData();
        healthData.setBraceletId("1");
        healthData.setMotionState("0");
        healthData.setPulseState(100);
        healthData.setTemperature(36.8f);
        HealthData.save(healthData);
    }

    @Test
    public void testMyLib() {
        System.out.println(new MyUtil().getName());
    }

    @Test
    public void testData() {

        byte[] header = new byte[]{(byte) 0x55, (byte) 0x33};
        byte[] length = new byte[]{(byte) 0x10};
        byte[] command = new byte[]{(byte) 0x01};

        byte[] motionState = new byte[]{(byte) 0x01};
        byte[] pulseState = new byte[]{(byte) 0x3C};
        byte[] tempertureState = new byte[]{(byte) 0x33, (byte) 0x38, (byte) 0x2E, (byte) 0x35};
        byte[] warringState = new byte[]{(byte) 0x01};

        byte[] sbpState = new byte[]{(byte) 0x08};
        byte[] dbpState = new byte[]{(byte) 0x10};

        byte[] userId = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07};
        byte[] end_code = new byte[]{(byte) 0x0D, (byte) 0x0A};
    }

    public static void main(String[] args) {
        System.out.println(getMotionState());
        System.out.println(getPluseState());

    }

    private static int getMotionState() {
        return getRadomNum(0, 1);
    }

    private static int getPluseState() {
        return getRadomNum(20, 100);
    }

    private static byte[] getTempertureState() {
        int temp1 = getRadomNum(30, 45);
        int temp2 = getRadomNum(0, 9);
        return new byte[]{(byte) 0x0, (byte) temp1, (byte) 0x2E, (byte) temp2};
    }

    private static int getWarringState() {
        return getRadomNum(0, 1);
    }

    private static int sbpState() {
        return getRadomNum(40, 60);
    }

    private static int dbpState() {
        return getRadomNum(100, 150);
    }

    private static int getRadomNum(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long) end - (long) start + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * random.nextDouble());
        return (int) (fraction + start);
    }

}
