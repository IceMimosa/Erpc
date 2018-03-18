package io.patamon.erpc.serialization.fastjson

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.parser.Feature
import com.alibaba.fastjson.serializer.SerializerFeature
import io.patamon.erpc.serialization.Serializer

/**
 * Desc: fastjson 实现
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/19
 */
class FastJsonSerializer : Serializer {

    /**
     * 将对象序列化成字节数组
     */
    override fun serialize(obj: Any): ByteArray =
            JSON.toJSONBytes(obj, SerializerFeature.SortField)

    /**
     * 将字节数组反序列化成指定对象
     */
    override fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T =
            JSON.parseObject(bytes, clazz, Feature.SortFeidFastMatch)

}