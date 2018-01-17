package com.sylar.netty.client.udp;

import com.sylar.netty.server.udp.LogEvent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> { // 1
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace(); // 2
		ctx.close();
	}
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		StringBuilder builder = new StringBuilder(); // 3
		LogEvent event = (LogEvent)msg;
		builder.append(event.getReceivedTimestamp());
		builder.append(" [");
		builder.append(event.getSource().toString());
		builder.append("] [");
		builder.append(event.getLogfile());
		builder.append("] : ");
		builder.append(event.getMsg());
		System.out.println(builder.toString()); // 4
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
		
		System.out.println("********************"); // 4
	}
}