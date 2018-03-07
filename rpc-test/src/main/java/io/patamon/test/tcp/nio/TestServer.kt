package io.patamon.test.tcp.nio

import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel

/**
 * Desc: nio server 端demo
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/7
 */
object TestServer {
    @JvmStatic
    fun main(args: Array<String>) {

        val serverChannel = ServerSocketChannel.open()
        serverChannel.configureBlocking(false)
        serverChannel.socket().bind(InetSocketAddress("127.0.0.1", 10010))

        val selector = Selector.open()
        serverChannel.register(selector, SelectionKey.OP_ACCEPT)
    }
}