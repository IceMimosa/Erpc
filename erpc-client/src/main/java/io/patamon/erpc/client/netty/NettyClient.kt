package io.patamon.erpc.client.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.patamon.erpc.client.ErpcClient
import io.patamon.erpc.codec.ErpcDecoder
import io.patamon.erpc.codec.ErpcEncoder
import io.patamon.erpc.common.model.ErpcRequest
import io.patamon.erpc.common.model.ErpcResponse
import io.patamon.erpc.registry.RegistryFactory
import io.patamon.erpc.serialization.SerializerFactory
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch

/**
 * Desc: netty client 实现
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
class NettyClient : ErpcClient {

    /**
     * 序列化
     */
    private val serializer = SerializerFactory.load()

    /**
     * 服务发现
     */
    private val registry = RegistryFactory.load()

    /**
     * 执行 rpc 请求
     */
    override fun call(request: ErpcRequest): ErpcResponse {
        // 1. 发现服务
        val address = registry.discover(request.className)
        val ( host, port ) = address.split(":")

        // 2. 创建客户端请求
        val response = ErpcResponse()
        val group = NioEventLoopGroup()
        try {
            val launch = CountDownLatch(1)
            val bootstrap = Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel::class.java)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(channel: SocketChannel) {
                            val pipeline = channel.pipeline()
                            // add pipeline
                            pipeline.addLast(ErpcEncoder(serializer))
                            pipeline.addLast(ErpcDecoder(serializer, ErpcResponse::class.java))
                            pipeline.addLast(NettyClientHandler(response))
                        }
                    })
            val future = bootstrap.connect(InetSocketAddress(host, port.toInt())).sync()
            // 3. 发送请求
            future.channel().writeAndFlush(request).addListener {
                launch.countDown()
            }

            // 一直等待到值返回
            launch.await()
            future.channel().closeFuture().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            group.shutdownGracefully()
        }
        return response
    }

}