package io.patamon.erpc.serialization

import io.patamon.erpc.serialization.kryo.KryoSerializer

/**
 * Desc: 序列化对象实例加载factory
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
object SerializerFactory {

    /**
     * 默认加载 Kryo, 这里可以通过配置文件的方式读取, 略......
     */
    fun load() = KryoSerializer()

}