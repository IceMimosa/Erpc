package io.patamon.erpc.server.netty

import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.patamon.erpc.common.model.ErpcRequest
import io.patamon.erpc.common.model.ErpcResponse
import org.slf4j.LoggerFactory

/**
 * Desc: netty handler 处理
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
class NettyServerHandler(
        // 待执行的服务类实例
        private val handlers: Map<String, Any?>
) : SimpleChannelInboundHandler<ErpcRequest>() {

    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 读取客户端数据, 然后执行对应的服务实例, 返回执行结果
     */
    override fun channelRead0(ctx: ChannelHandlerContext, request: ErpcRequest) {
        log.info("Request is $request")
        val response = handle(request)
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE)
    }

    /**
     * 处理请求
     */
    private fun handle(request: ErpcRequest): ErpcResponse {
        val response = ErpcResponse(request.requestId)
        try {
            val bean = handlers[request.className]
                    ?: throw RuntimeException("Do not have instance for class ${request.className}")
            val method = bean.javaClass.getMethod(request.methodName, *request.parameterTypes)
                    ?: throw RuntimeException("Do not have method for class ${request.className}, method name is ${request.methodName}")
            val result = method.invoke(bean, request.params)
            response.result = result
        } catch (e: Exception) {
            response.error = e
        }
        return response
    }

}