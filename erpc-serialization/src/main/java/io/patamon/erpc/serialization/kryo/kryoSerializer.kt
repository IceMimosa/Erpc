package io.patamon.erpc.serialization.kryo

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import io.patamon.erpc.serialization.Serializer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Desc: Kryo 序列化实现
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/19
 */
class kryoSerializer : Serializer {
    /**
     * 将对象序列化成字节数组
     */
    override fun serialize(obj: Any): ByteArray {
        val kryo = Kryo()
        val baos = ByteArrayOutputStream()
        val output = Output(baos)
        kryo.writeObject(output, obj)

        return output.use {
            it.flush()
            // 返回baos字节数组
            baos.toByteArray()
        }
    }

    /**
     * 将字节数组反序列化成指定对象
     */
    override fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T {
        val kryo = Kryo()
        val bais = ByteArrayInputStream(bytes)
        val input = Input(bais)

        return input.use {
            kryo.readObject(it, clazz)
        }
    }


}