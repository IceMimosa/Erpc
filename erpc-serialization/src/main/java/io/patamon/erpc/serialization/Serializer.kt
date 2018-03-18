package io.patamon.erpc.serialization

/**
 * Desc: 序列化对象的接口
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/19
 */
interface Serializer {

    /**
     * 将对象序列化成字节数组
     */
    fun serialize(obj: Any): ByteArray


    /**
     * 将字节数组反序列化成指定对象
     */
    fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T

}