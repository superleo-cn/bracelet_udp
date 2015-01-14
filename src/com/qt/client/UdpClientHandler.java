package com.qt.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import org.apache.log4j.Logger;

public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private static final Logger LOGGER = Logger.getLogger(UdpClientHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOGGER.error("[UdpClientHandler] -> [Read client failed]", cause);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
		String response = msg.content().toString(CharsetUtil.US_ASCII);
		LOGGER.info("[UdpClientHandler] -> [channelRead0] -> [receive data] : " + response);
		ctx.close();
	}
}