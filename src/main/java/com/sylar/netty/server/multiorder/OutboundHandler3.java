package com.sylar.netty.server.multiorder;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutboundHandler3 extends ChannelOutboundHandlerAdapter {
	private static Logger	logger	= Logger.getLogger(OutboundHandler3.class);
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		logger.info("OutboundHandler3.write");
		System.out.println("*************outboundHandler3************");
		// 执行下一个OutboundHandler
    	super.write(ctx, msg, promise);
	}
}
