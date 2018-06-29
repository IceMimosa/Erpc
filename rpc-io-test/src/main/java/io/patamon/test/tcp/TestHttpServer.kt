package io.patamon.test.tcp

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

/**
 * Desc: http server demo
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2017/3/19
 */
object TestHttpServer {

    @JvmStatic
    fun main(args: Array<String>) {
        // 100: 接收的请求数
        val server = HttpServer.create(InetSocketAddress("127.0.0.1", 18888), 100)
        // 创建context的路径
        server.createContext("/", { exchange ->
            println(exchange.requestMethod)
            println(exchange.protocol)
            println(exchange.requestURI)
            exchange.requestHeaders.forEach { attr, value ->
                println("$attr >>> $value")
            }
            println(String(exchange.requestBody.readBytes()))

            val res = "<font color='#ff0000'>Hello! This a HttpServer!</font>"
            exchange.sendResponseHeaders(200, res.length.toLong())
            val out = exchange.responseBody
            out.write(res.toByteArray())
            out.close()
        })
        server.start()
    }
}
