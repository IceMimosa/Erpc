package io.patamon.test.tcp.netty

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress
import java.nio.charset.Charset

/**
 * Desc: netty client
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/9
 */
object TestClient {

    @JvmStatic
    fun main(args: Array<String>) {
        val group = NioEventLoopGroup()
        val bootstrap = Bootstrap()
        bootstrap.group(group)
                .channel(NioSocketChannel::class.java)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        val pipeline = ch.pipeline()
                        // 添加pipeline
                        pipeline.addLast("handler", ClientChannelHandler())
                    }
                })
        val future = bootstrap.connect(InetSocketAddress("localhost", 10080)).sync()
        future.channel().closeFuture().sync()

        // 关闭group
        group.shutdownGracefully()
    }

}

class ClientChannelHandler : ChannelInboundHandlerAdapter() {
    /**
     * Calls [ChannelHandlerContext.fireChannelRead] to forward
     * to the next [ChannelInboundHandler] in the [ChannelPipeline].
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        // super.channelRead(ctx, msg)
        val buf = msg as ByteBuf
        println("From Server Channel msg: ${buf.readCharSequence(buf.readableBytes(), Charset.defaultCharset())}")
        ctx.close()
    }

    /**
     * Calls [ChannelHandlerContext.fireChannelActive] to forward
     * to the next [ChannelInboundHandler] in the [ChannelPipeline].
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun channelActive(ctx: ChannelHandlerContext) {
        // super.channelActive(ctx)
        println("Client Channel in active...")

        val buffer = Unpooled.copiedBuffer("Hello Server !!!".toByteArray())
        ctx.writeAndFlush(buffer)
    }

    /**
     * Calls [ChannelHandlerContext.fireExceptionCaught] to forward
     * to the next [ChannelHandler] in the [ChannelPipeline].
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        // super.exceptionCaught(ctx, cause)
        println("Client Channel in exception caught...")
    }
}