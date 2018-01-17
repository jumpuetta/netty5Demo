package com.sylar.netty.server.spdy;

import javax.net.ssl.SSLEngine;

import org.eclipse.jetty.npn.NextProtoNego;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

public class SpdyChannelInitializer extends ChannelInitializer<SocketChannel> { 
	
	private final SslContext context;
	
	public SpdyChannelInitializer(SslContext context) {
		this.context = context;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		SSLEngine engine = context.newEngine(ch.alloc()); 
		engine.setUseClientMode(false); 
		NextProtoNego.put(engine, new DefaultServerProvider()); 
		NextProtoNego.debug = true;
		pipeline.addLast("sslHandler", new SslHandler(engine)); 
		pipeline.addLast("chooser",
				new DefaultSpdyOrHttpChooser(1024 * 1024, 1024 * 1024));
	}
}