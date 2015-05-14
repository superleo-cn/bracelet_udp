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

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

public final class UdpClient {

    private static final Logger LOGGER = Logger.getLogger(UdpClient.class);

    static final int PORT = 8080;

    // static final String IP = "218.212.108.250";
    static final String IP = "54.254.145.129";
    //static final String IP = "localhost";

    static final String HEX_VALUE = new String(
            new byte[]{(byte) 0x55, (byte) 0x33, (byte) 0x10, (byte) 0x01, (byte) 0x01, (byte) 0x3C, (byte) 0x33, (byte) 0x38, (byte) 0x2E, (byte) 0x35, (byte) 0x01,
                    (byte) 0x08, (byte) 0x10, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x0D, (byte) 0x0A});

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new UdpClientHandler());

            Channel ch = b.bind(0).sync().channel();

            // Broadcast the QOTM request to port 8080.
            ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(HEX_VALUE, CharsetUtil.US_ASCII), new InetSocketAddress(IP, PORT))).sync();

            // QuoteOfTheMomentClientHandler will close the DatagramChannel when
            // a
            // response is received. If the channel is not closed within 5
            // seconds,
            // print an error message and quit.
            if (!ch.closeFuture().await(5000)) {
                LOGGER.error("Request timed out.");
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}