package com.qt.server;

import com.qt.process.ProcessParsing;
import com.qt.process.ProcessParsingFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.qt.constant.Constants;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private static final Logger LOGGER = Logger.getLogger(UdpServerHandler.class);

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOGGER.error("[UdpServerHandler] -> [Read client failed]", cause);
		// We don't close the channel because we can keep serving requests.
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		String result = packet.content().toString(CharsetUtil.US_ASCII);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[UdpServerHandler] -> [channelRead0] -> " + packet);
			LOGGER.debug("[UdpServerHandler] -> [channelRead0] -> " + Hex.encodeHexString(result.getBytes()));
		}
		String response = Constants.NO;
		if (StringUtils.isNotEmpty(result)) {
			ProcessParsing parsing = ProcessParsingFactory.createParsing(result);
			if (parsing != null) {
				response = Constants.OK;
			}
			writeToClient(response, ctx, packet);
			// store into database
			parsing.process();
		} else {
			// should I block the invalid IP client?
			writeToClient(response, ctx, packet);
		}
	}

	private void writeToClient(String result, ChannelHandlerContext ctx, DatagramPacket packet) {
		ctx.write(new DatagramPacket(Unpooled.copiedBuffer(result, CharsetUtil.US_ASCII), packet.sender()));
	}

}