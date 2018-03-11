package io.patamon.test.tcp.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.string.StringDecoder
import java.net.InetSocketAddress
import java.nio.charset.Charset

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

        val bossGroup = NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2)
        val workGroup = NioEventLoopGroup(5)

        val bootstrap = ServerBootstrap()
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(object : ChannelInitializer<Channel>() {
                    override fun initChannel(ch: Channel) {
                        val pipeline = ch.pipeline()
                        // 添加pipeline
                        pipeline.addLast(StringDecoder(Charset.defaultCharset()))
                        pipeline.addLast(StringDecoder(Charset.defaultCharset()))
                        pipeline.addLast(ServerChannelHandler())
                    }
                })
        val future = bootstrap.bind(InetSocketAddress("localhost", 10080)).sync()
        future.channel().closeFuture().sync()

        // bossGroup.shutdownGracefully()
        // workGroup.shutdownGracefully()
    }
}

class ServerChannelHandler : ChannelInboundHandlerAdapter() {

    /**
     * Calls [ChannelHandlerContext.fireChannelRead] to forward
     * to the next [ChannelInboundHandler] in the [ChannelPipeline].
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        // super.channelRead(ctx, msg)
        val buf = msg as ByteBuf
        println("Server Channel msg: ${buf.readCharSequence(buf.readableBytes(), Charset.defaultCharset())}")

        // 写
        val buffer = Unpooled.buffer(4096)
        buffer.writeBytes("Hello client...".toByteArray())
        ctx.writeAndFlush(buffer)
    }

    /**
     * Calls [ChannelHandlerContext.fireChannelActive] to forward
     * to the next [ChannelInboundHandler] in the [ChannelPipeline].
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun channelActive(ctx: ChannelHandlerContext?) {
        super.channelActive(ctx)
        println("Server Channel in active...")
    }

    /**
     * Calls [ChannelHandlerContext.fireExceptionCaught] to forward
     * to the next [ChannelHandler] in the [ChannelPipeline].
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        // super.exceptionCaught(ctx, cause)
        println("Server Channel in exception caught...$cause")
    }
}