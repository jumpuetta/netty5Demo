package com.sylar.netty.client.linebasedframe;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {

private static final Logger LOGGER = Logger
        .getLogger(TimeClientHandler.class.getName());
private byte[] req;
private int counter;

public TimeClientHandler() {
    req = ("query time order"+System.getProperty("line.separator")).getBytes();//
}

@Override
public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ByteBuf message=null;
    for(int i=0;i<1000;i++){
        message=Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);
    }
}

@Override
public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception {
    LOGGER.warn("Unexpected exception from downstrema : "
            + cause.getMessage());
    ctx.close();
}

@Override
public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    String body=(String)msg;
    System.out.println("Now is : " + body+"; the counter is : "+ ++counter);

}
}