package com.sylar.netty.server.multiorder;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InboundHandler1 extends ChannelInboundHandlerAdapter {
	private static Logger	logger	= Logger.getLogger(InboundHandler1.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("InboundHandler1.channelRead: ctx :" + ctx);
		// 通知执行下一个InboundHandler
		System.out.println("*************inboundHandler1************");
		ctx.pipeline().context(InboundHandler2.class).fireChannelRead(msg);
		//ctx.fireChannelRead(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.info("InboundHandler1.channelReadComplete");
		ctx.flush();
	}
}
