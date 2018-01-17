package com.sylar.netty.server.multiorder;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InboundHandler3 extends ChannelInboundHandlerAdapter {
	private static Logger	logger	= Logger.getLogger(InboundHandler3.class);

	@Override
	// 读取Client发送的信息，并打印出来
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("InboundHandler3.channelRead: ctx :" + ctx);
		System.out.println("*************inboundHandler3************");
		ByteBuf result = (ByteBuf) msg;
		byte[] result1 = new byte[result.readableBytes()];
		result.readBytes(result1);
		String resultStr = new String(result1);
		System.out.println("Client said:" + resultStr);
		result.release();

		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.info("InboundHandler3.channelReadComplete");
		ctx.flush();
	}

}
