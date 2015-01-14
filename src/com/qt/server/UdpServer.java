package com.qt.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import org.apache.log4j.Logger;

/**
 * A UDP server that responds to the QOTM (quote of the moment) request to a
 * {@link QuoteOfTheMomentClient}.
 *
 * Inspired by <a href=
 * "http://docs.oracle.com/javase/tutorial/networking/datagrams/clientServer.html"
 * >the official Java tutorial</a>.
 */
public final class UdpServer {

	private static final Logger LOGGER = Logger.getLogger(UdpServer.class);
	private static final int PORT = 80;

	public static void main(String[] args) throws Exception {
		LOGGER.info("==========[UDP Server Init]==========");
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new UdpServerHandler());
			LOGGER.info("==========[UDP Server Bind Port : " + PORT + "]==========");
			LOGGER.info("==========[UDP Server Started]==========");
			b.bind(PORT).sync().channel().closeFuture().await();
		} catch (Exception e) {
			LOGGER.error("[UdpServer] -> [started failed]", e);
		} finally {
			group.shutdownGracefully();
		}
	}
}