package io.patamon.erpc.server.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.patamon.erpc.common.model.ErpcRequest
import io.patamon.erpc.serialization.SerializerFactory
import io.patamon.erpc.server.ErpcServer
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress

/**
 * Desc: netty server
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
class NettyServer : ErpcServer {

    val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 序列化
     */
    private val serializer = SerializerFactory.load()

    /**
     * 服务实例
     */
    private val handlers = mutableMapOf<String, Any?>()

    /**
     * 运行 server
     */
    override fun run(hostname: String, port: Int) {
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
                        override fun initChannel(channel: Channel) {
                            val pipeline = channel.pipeline()
                            // add pipeline
                            pipeline.addLast(ErpcDecoder(serializer, ErpcRequest::class.java))
                            pipeline.addLast(ErpcEncoder(serializer))
                            pipeline.addLast(NettyServerHandler(handlers))
                        }
                    })
            val future = bootstrap.bind(InetSocketAddress(hostname, port)).sync()
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

    /**
     * 注册服务实例
     */
    override fun publish(bean: Any) {
        handlers[bean.javaClass.name] = bean
    }
}