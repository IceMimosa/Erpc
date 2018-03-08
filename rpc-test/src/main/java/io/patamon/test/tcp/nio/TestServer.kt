package io.patamon.test.tcp.nio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

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
        // 1. 创建选择器
        val selector = Selector.open()
        // 2. 打开监听信道
        val serverChannel = ServerSocketChannel.open()
        serverChannel.configureBlocking(false) // 设置为非阻塞
        serverChannel.bind(InetSocketAddress("127.0.0.1", 10010))
        // 注册监听的事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT)

        // 3. 反复循环, 等待IO
        while (true) {
            // 1. 等待某个信道就绪
            val keys = selector.select()
            if (keys == 0) {
                // println("等待信道就绪...")
                continue
            }
            // 2. 取出准备好的信道
            val selectedKeys = selector.selectedKeys()
            val iterator = selectedKeys.iterator()
            while (iterator.hasNext()) {
                // 获取某个信道，并移除
                val selectionKey = iterator.next()
                iterator.remove()
                // 业务代码

                // > 可接收的channel
                if (selectionKey.isAcceptable) {
                    println("服务端收到连接")
                    // 获取客户端连接, 并注册事件
                    val client = (selectionKey.channel() as ServerSocketChannel).accept()
                    client.configureBlocking(false)
                    client.register(selector, SelectionKey.OP_READ)
                }
                // > 读事件
                else if (selectionKey.isReadable) {
                    val client = selectionKey.channel() as SocketChannel
                    // 获取并情况缓冲区
                    val buffer = ByteBuffer.allocate(4096)
                    buffer.clear()
                    // 读取数据
                    val count = client.read(buffer)
                    if (count > 0) {
                        val readStr = String(buffer.array(), 0, count)
                        println("服务端读到数据: $readStr")
                        // 注册写服务
                        client.register(selector, SelectionKey.OP_WRITE)
                    }
                }
                // > 写事件
                else if (selectionKey.isWritable) {
                    val client = selectionKey.channel() as SocketChannel
                    // 发送数据
                    val buffer = ByteBuffer.allocate(4096)
                    buffer.put("hello client".toByteArray())
                    buffer.flip()
                    client.write(buffer)

                    // 设置下一次为读或写
                    selectionKey.interestOps(SelectionKey.OP_READ)
                }
            }
        }
    }
}