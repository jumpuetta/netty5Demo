package com.sylar.netty.server.spdy;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		if (HttpHeaderUtil.is100ContinueExpected(request)) {
			send100Continue(ctx); 
		}
		FullHttpResponse response = new DefaultFullHttpResponse(
				request.protocolVersion(), HttpResponseStatus.OK); 
		response.content().writeBytes(getContent().getBytes(CharsetUtil.UTF_8)); 
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,
				"text/plain; charset=UTF-8"); 
		boolean keepAlive = HttpHeaderUtil.isKeepAlive(request);
		if (keepAlive) { 
			response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
					response.content().readableBytes());
			response.headers().set(HttpHeaderNames.CONNECTION,
					HttpHeaderValues.KEEP_ALIVE);
		}
		ChannelFuture future = ctx.writeAndFlush(response); 
		if (!keepAlive) {
			future.addListener(ChannelFutureListener.CLOSE); 
		}
	}
	
	protected String getContent() { 
		return "This content is transmitted via HTTP\r\n";
	}
	
	private static void send100Continue(ChannelHandlerContext ctx) { 
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception { 
		cause.printStackTrace();
		ctx.close();
	}
	
}