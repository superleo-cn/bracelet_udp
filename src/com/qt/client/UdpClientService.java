package com.qt.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.*;

public final class UdpClientService {

    private static final Random random = new Random();

    private static final Logger LOGGER = Logger.getLogger(UdpClientService.class);

    static final int PORT = 8080;

    static final String IP = "54.254.145.129";
    // static final String IP = "localhost";

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Runnable task = () -> {
            try {
                while (true) {
                    byte[] value = generateData();
                    final String HEX_VALUE = new String(value);
                    Bootstrap b = new Bootstrap();
                    b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new UdpClientHandler());
                    final Channel ch = b.bind(0).sync().channel();
                    LOGGER.info("Task is running at: " + new Date());
                    ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(HEX_VALUE, CharsetUtil.US_ASCII), new InetSocketAddress(IP, PORT))).sync();
                    if (!ch.closeFuture().await(5000)) {
                        LOGGER.error("Request timed out.");
                    }
                    Thread.sleep(1000 * 30) ;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }


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

    private static byte[] generateData() {
        List<Byte> list = new ArrayList<>();

        Byte[] header = new Byte[]{(byte) 0x55, (byte) 0x33};
        Byte[] length = new Byte[]{(byte) 0x10};
        Byte[] command = new Byte[]{(byte) 0x01};

        int motionState = getMotionState();
        int pluseState = getPluseState();
        Byte[] templateState = getTempertureState();
        int warningState = getWarningState();
        int sbpState = getSbpState();
        int dbpState = getDbpState();

        Byte[] userId = new Byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        Byte[] end_code = new Byte[]{(byte) 0x0D, (byte) 0x0A};

        addToList(list, header);
        addToList(list, length);
        addToList(list, command);
        list.add((byte) motionState);
        list.add((byte) pluseState);
        addToList(list, templateState);
        list.add((byte) warningState);
        list.add((byte) sbpState);
        list.add((byte) dbpState);
        addToList(list, userId);
        addToList(list, end_code);

        Byte[] oBytes = list.toArray(new Byte[0]);
        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }


    private static void addToList(List<Byte> list, Byte[] bytes) {
        List<Byte> subList = Arrays.asList(bytes);
        subList.stream().forEach(b -> list.add(b));
    }

    private static int getMotionState() {
        return getRadomNum(0, 1);
    }

    private static int getPluseState() {
        return getRadomNum(20, 100);
    }

    private static Byte[] getTempertureState() {
        int temp1 = getRadomNum(3, 3) + 48;
        int temp2 = getRadomNum(0, 9) + 48;
        return new Byte[]{(byte) temp1, (byte) temp2, (byte) 0x2E, (byte) temp2};
    }

    private static int getWarningState() {
        return getRadomNum(0, 1);
    }

    private static int getSbpState() {
        return getRadomNum(40, 60);
    }

    private static int getDbpState() {
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