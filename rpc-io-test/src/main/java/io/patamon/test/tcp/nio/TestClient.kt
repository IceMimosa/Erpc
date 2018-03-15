package io.patamon.test.tcp.nio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel

/**
 * Desc: nio client 端demo
 *
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/7
 */
object TestClient {

    @JvmStatic
    fun main(args: Array<String>) {
        // 1. 创建选择器
        val selector = Selector.open()
        // 2. 打开监听信道
        val socketChannel = SocketChannel.open()
        socketChannel.configureBlocking(false)
        socketChannel.connect(InetSocketAddress("127.0.0.1", 10010))

        // 注册连接事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT)

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
                // 业务代码

                // > 可连接的channel
                if (selectionKey.isConnectable) {
                    // 获取客户端连接, 并注册事件
                    val client = selectionKey.channel() as SocketChannel
                    if (client.isConnectionPending) {
                        client.finishConnect()
                        println("客户端连接成功")

                        // 发送数据
                        val buffer = ByteBuffer.allocate(4096)
                        buffer.put("hello server".toByteArray())
                        buffer.flip()
                        client.write(buffer)
                    }
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
                        buffer.flip()
                        val readStr = String(buffer.array(), 0, count)
                        println("客户端收到数据: $readStr")
                    }
                }
                // > 写事件
                else if (selectionKey.isWritable) {
                    // 写
                }
            }
        }
    }
}
