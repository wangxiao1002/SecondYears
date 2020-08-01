package com.sy.nettyrpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author wangxiao
 * @since 1.1
 */
public class NettyRpcInvocationHandler   implements InvocationHandler {

    private Class<?> type;

    public NettyRpcInvocationHandler(Class<?> type) {
        this.type = type;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcInfo rpcInfo = new RpcInfo();
        rpcInfo.setClassName(type.getName());
        rpcInfo.setMethodName(method.getName());
        rpcInfo.setParamTypes(method.getParameterTypes());
        rpcInfo.setParams(args);

        //使用netty发送调用信息给服务提供方
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        RPCClientHandler rpcClientHandler = new RPCClientHandler();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectEncoder());
                            //反序列化对象时指定类解析器，null表示使用默认的类加载器
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 64, ClassResolvers.cacheDisabled(null)));
                            ch.pipeline().addLast(rpcClientHandler);

                        }
                    });
            //connect是异步的，但调用其future的sync则是同步等待连接成功
            ChannelFuture future = bootstrap.connect("127.0.0.1", 80).sync();
            //同步等待调用信息发送成功
            future.channel().writeAndFlush(rpcInfo).sync();
            //同步等待RPCClientHandler的channelRead被触发后（意味着收到了调用结果）
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        //返回调用结果
        return rpcClientHandler.getRpcResult();

    }
}
