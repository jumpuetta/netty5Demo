package com.sylar.netty.server.base;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class NettyServerHandler1 extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		buf.markReaderIndex();
		buf.resetReaderIndex();
		String recieved = getMessage(buf);
		System.out.println("Message from Client (Handler1) : " + recieved);
       	try {
			ReferenceCountUtil.retain(msg);
     		ctx.fireChannelRead(getSendByteBuf("Handler1->Handler2"));
     		ctx.write(msg);
     		ctx.writeAndFlush(getSendByteBuf("APPLE1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	private String getMessage(ByteBuf buf) {

		byte[] con = new byte[buf.readableBytes()];
		buf.readBytes(con);
		try {
			return new String(con, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ByteBuf getSendByteBuf(String message) throws UnsupportedEncodingException {

		byte[] req = message.getBytes("UTF-8");
		ByteBuf pingMessage = Unpooled.buffer();
		pingMessage.writeBytes(req);

		return pingMessage;
	}
	
}