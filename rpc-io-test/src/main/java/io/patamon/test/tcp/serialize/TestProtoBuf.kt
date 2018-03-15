package io.patamon.test.tcp.serialize

import io.patamon.test.tcp.serialize.model.PersonProto
import java.util.*

/**
 * Desc: Protobuf简单的demo
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/15
 */
object TestProtoBuf {
    @JvmStatic
    fun main(args: Array<String>) {
        // 构建一个对象
        val person = PersonProto.Person.newBuilder()
                .setId(1L)
                .setAge(22)
                .setName("Hello")
                .build()

        // 序列化
        val bytes = person.toByteArray()
        println(Arrays.toString(bytes))

        // 反序列化
        val personProto = PersonProto.Person.parseFrom(bytes)
        println(personProto)
    }
}