package io.patamon.test.tcp.bio

import java.net.ServerSocket
import java.net.Socket

/**
 * Desc: test server
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2017/3/19
 */
object TestServer {

    @JvmStatic
    fun main(args: Array<String>) {

        val server = ServerSocket(18888)
        while (true) {
            val socket = server.accept()
            Thread(ServerThread(socket)).start()
        }
    }
}

class ServerThread(private val socket: Socket) : Runnable {

    override fun run() {
        // 获取请求内容
        val input = socket.getInputStream()
        val reader = input.bufferedReader()
        var line = reader.readLine()
        while (line != null) {
            println(line)
            line = reader.readLine() // 会阻塞，对于http请求需要理解 http 请求头组成
        }

        // 响应
        val out = socket.getOutputStream()
        val ow = out.writer()
        ow.write("server: 我收到啦!!!!")
        ow.flush()

        // 关闭资源
        ow.close()
        out.close()
        reader.close()
        input.close()
        socket.close()
    }
}