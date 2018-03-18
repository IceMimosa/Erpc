package io.patamon.erpc.serialization.protostuff

import io.patamon.erpc.serialization.Serializer
import io.protostuff.LinkedBuffer
import io.protostuff.ProtostuffIOUtil
import io.protostuff.runtime.RuntimeSchema


/**
 * Desc: Protostuff 实现
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/19
 */
class ProtostuffSerializer : Serializer {
    /**
     * 将对象序列化成字节数组
     */
    override fun serialize(obj: Any): ByteArray {
        val schema = RuntimeSchema.createFrom(obj.javaClass)
        val buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer)
        } finally {
            buffer.clear()
        }
    }

    /**
     * 将字节数组反序列化成指定对象
     */
    override fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T {
        val schema = RuntimeSchema.createFrom(clazz)
        val message = schema.newMessage()
        ProtostuffIOUtil.mergeFrom(bytes, message, schema)
        return message
    }
}