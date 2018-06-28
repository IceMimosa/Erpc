package io.patamon.erpc.server.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import io.patamon.erpc.serialization.Serializer

/**
 * Desc: rpc 消息编码器
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
class ErpcEncoder(
        private val serializer: Serializer
) : MessageToByteEncoder<Any>() {

    /**
     * 编码, 使用 `长度`+`数据` 的方式
     */
    override fun encode(ctx: ChannelHandlerContext, msg: Any, out: ByteBuf) {
        val bytes = serializer.serialize(msg)
        // 写出数据
        out.writeInt(bytes.size)
        out.writeBytes(bytes)
    }
}
