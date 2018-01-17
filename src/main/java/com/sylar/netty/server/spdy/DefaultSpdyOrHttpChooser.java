package com.sylar.netty.server.spdy;

import javax.net.ssl.SSLEngine;

import org.eclipse.jetty.npn.NextProtoNego;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;

public class DefaultSpdyOrHttpChooser extends SpdyOrHttpChooser {
	
	public DefaultSpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
		super(maxSpdyContentLength, maxHttpContentLength);
	}
	
	@Override
	protected SelectedProtocol getProtocol(SSLEngine engine) {
//		DefaultServerProvider provider = (DefaultServerProvider) NextProtoNego
//				.get(engine); // 1
//		String protocol = provider.getSelectedProtocol();
//		if (protocol == null) {
//			return SelectedProtocol.UNKNOWN; // 2
//		}
//		switch (protocol) {
//			case "spdy/3.1" :
//				return SelectedProtocol.SPDY_3_1; // 4
//			case "http/1.1" :
//				return SelectedProtocol.HTTP_1_1; // 5
//			default :
//				return SelectedProtocol.UNKNOWN; // 6
//		}
		return null;
	}
	
	@Override
	protected ChannelHandler createHttpRequestHandlerForHttp() {
		return new HttpRequestHandler(); // 7
	}
	
	@Override
	protected ChannelHandler createHttpRequestHandlerForSpdy() {
		return new SpdyRequestHandler(); // 8
	}
}