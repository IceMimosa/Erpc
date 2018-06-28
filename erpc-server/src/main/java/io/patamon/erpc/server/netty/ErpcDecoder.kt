package io.patamon.erpc.server.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.patamon.erpc.serialization.Serializer

/**
 * Desc: rpc 消息解码器
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
class ErpcDecoder(
        private val serializer: Serializer,
        private val clazz: Class<*>
) : ByteToMessageDecoder() {

    /**
     * 解码, 对数据进行拆包
     */
    override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf, out: MutableList<Any>) {
        // 1. 读取字节长度
        if (byteBuf.readableBytes() < 4) {
            return
        }
        val length = byteBuf.readInt()
        if (byteBuf.readableBytes() < length) {
            throw RuntimeException("Insufficient bytes to be read, expected: $length")
        }
        // 2. 读取数据, 并反序列化返回
        val bytes = byteBuf.readBytes(length)
        val obj = serializer.deserialize(bytes.array(), clazz)
        out.add(obj)
    }

}
