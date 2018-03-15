package io.patamon.test.tcp.serialize

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import io.patamon.test.tcp.serialize.model.Person
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * Desc: Kyro Test Demo
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/15
 */
object TestKryo {

    @JvmStatic
    fun main(args: Array<String>) {
        val person = Person(1L, 22, "Hello")

        // 序列化
        val bytes = serialize(person)
        println(Arrays.toString(bytes))

        // 反序列
        val personKryo = deserialize(bytes, Person::class.java)
        println(personKryo)
    }

    private val kryo = Kryo()
    /**
     * 序列化
     */
    fun serialize(o: Any): ByteArray {
        val baos = ByteArrayOutputStream()
        val output = Output(baos)
        kryo.writeObject(output, o)

        return output.use {
            it.flush()
            // 返回baos字节数组
            baos.toByteArray()
        }
    }

    /**
     * 反序列化
     */
    fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T {
        val bais = ByteArrayInputStream(bytes)
        val input = Input(bais)

        return input.use {
            kryo.readObject(it, clazz)
        }
    }
}