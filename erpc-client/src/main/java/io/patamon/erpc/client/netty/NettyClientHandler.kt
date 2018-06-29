package io.patamon.erpc.client.netty

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.patamon.erpc.common.model.ErpcResponse
import org.slf4j.LoggerFactory

/**
 * Desc: netty client handler
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
class NettyClientHandler(
        // 需要返回的 response 对象
        private val responseValue: ErpcResponse
) : SimpleChannelInboundHandler<ErpcResponse>() {

    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 处理服务端返回的数据
     */
    override fun channelRead0(ctx: ChannelHandlerContext, response: ErpcResponse) {
        log.info("Response is $response")
        responseValue.requestId = response.requestId
        responseValue.result = response.result
        responseValue.error = response.error
    }
}