package com.sylar.netty.server.linebasedframe;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

    public void bind(int port) throws InterruptedException {
        // ����NIO�߳���
        EventLoopGroup bossGroup = new NioEventLoopGroup();// �����߳�
        EventLoopGroup workerGroup = new NioEventLoopGroup();// �����߳���
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childHandler(new ChildChannelHandler());
            // �󶨶˿ڣ�ͬ���ȴ��ɹ�
            ChannelFuture future = bootstrap.bind(port).sync();
            // �ȴ�����˼����˿ڹرգ��ȴ��������·�ر�֮��main�������˳�
            future.channel().closeFuture().sync();
        } finally {
            // �����˳����ͷ��̳߳���Դ
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            //������
            //ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
          //  ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new TimeServerHandler());


        }

    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new TimeServer().bind(port);

    }

}