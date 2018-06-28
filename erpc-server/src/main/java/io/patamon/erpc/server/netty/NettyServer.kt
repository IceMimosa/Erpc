package io.patamon.erpc.server.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.patamon.erpc.server.ErpcServer
import io.patamon.erpc.server.utils.SocketUtils.findAvailableTcpPort
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress

/**
 * Desc:
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
class NettyServer : ErpcServer {

    val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 运行 server
     */
    override fun run() {
        val bossGroup = NioEventLoopGroup()
        val workGroup = NioEventLoopGroup()
        try {
            val bootstrap = ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel::class.java)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(object : ChannelInitializer<Channel>() {
                        override fun initChannel(ch: Channel) {
                            // TODO
                        }
                    })
            val port = findAvailableTcpPort()
            val future = bootstrap.bind(InetSocketAddress("127.0.0.1", port)).sync()
            if (future.isSuccess) {
                log.info("Init erpc server success, port is {}", port)
            }
            future.channel().closeFuture().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bossGroup.shutdownGracefully()
            workGroup.shutdownGracefully()
        }
    }
}