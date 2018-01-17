package com.sylar.netty.server.linebasedframe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private int counter;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        String body=(String)msg;
        System.out.println("the time server receive order : " + body
                + " ;the counter is "+ ++counter);
        String currentTime = "query time order".equalsIgnoreCase(body) ? new Date(
                System.currentTimeMillis()).toString() : "bad order";
        currentTime+=System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);// ���ܿ��ǣ����������͵���Ϣ���͵����������У���ͨ������flush������д��channel��
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();// ����Ϣ���Ͷ����е���Ϣд�뵽SocketChannel�з��͸��Է���
    }

}