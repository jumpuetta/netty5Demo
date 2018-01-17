package com.sylar.netty.server.websocket.secure;

import java.net.InetSocketAddress;

import com.sylar.netty.server.websocket.ChatServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class SecureChatServer extends ChatServer {// 1
	private final SslContext context;
	public SecureChatServer(SslContext context) {
		this.context = context;
	}
	@Override
	protected ChannelInitializer<Channel> createInitializer(
			ChannelGroup group) {
		return new SecureChatServerIntializer(group, context); // 2
	}
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Please give port as argument");
			System.exit(1);
		}
		int port = Integer.parseInt(args[0]);
		SelfSignedCertificate cert = new SelfSignedCertificate();
		SslContext context = SslContext.newServerContext(cert.certificate(),
				cert.privateKey());
		final SecureChatServer endpoint = new SecureChatServer(context);
		ChannelFuture future = endpoint.start(new InetSocketAddress(port));
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				endpoint.destroy();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}