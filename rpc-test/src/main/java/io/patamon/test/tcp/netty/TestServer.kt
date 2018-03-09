package io.patamon.test.tcp.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.InetSocketAddress

/**
 * Desc: netty server
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/9
 */
object TestServer {

    @JvmStatic
    fun main(args: Array<String>) {

        val bossGroup = NioEventLoopGroup()
        val workGroup = NioEventLoopGroup()

        val bootstrap = ServerBootstrap()
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(object : ChannelInitializer<Channel>() {
                    override fun initChannel(ch: Channel) {
                        val pipeline = ch.pipeline()

                    }
                })
        val future = bootstrap.bind(InetSocketAddress("", 10080)).sync()
        future.channel().closeFuture().sync()
    }
}