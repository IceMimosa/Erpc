package io.patamon.test.tcp.bio

import java.net.Socket

/**
 * Desc: test client
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2017/3/19
 */
object TestClient {

    @JvmStatic
    fun main(args: Array<String>) {
        val socket = Socket("127.0.0.1", 18888)

        // 发出请求
        val out = socket.getOutputStream()
        val ow = out.writer()
        ow.write("client: 你好, 服务端!!!")
        ow.flush()
        socket.shutdownOutput()

        // 获取响应
        val input = socket.getInputStream()
        println(String(input.readBytes()))

        input.close()
        ow.close()
        out.close()
        socket.close()
    }
}