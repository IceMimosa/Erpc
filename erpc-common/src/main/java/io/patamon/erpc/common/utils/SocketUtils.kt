package io.patamon.erpc.common.utils

import java.net.InetAddress
import java.util.*
import javax.net.ServerSocketFactory

/**
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
object SocketUtils {

    /** random */
    private val random = Random(System.currentTimeMillis())

    /**
     * 查找机器可用的端口
     */
    fun findAvailableTcpPort(minPort: Int = 1024, maxPort: Int = 65535): Int {
        val portRange = maxPort - minPort
        var tryCounter = 0
        var okPort: Int
        do {
            if (++tryCounter > portRange) {
                throw RuntimeException("Could not find available tcp port !!!")
            }
            okPort = random.nextInt(portRange + 1)
        } while (!isTcpPortAvailable(okPort))
        return okPort
    }

    /** 校验 tcp port 是否合法 */
    private fun isTcpPortAvailable(port: Int): Boolean {
        try {
            val socket = ServerSocketFactory.getDefault()
                    .createServerSocket(port, 1, InetAddress.getByName("localhost"))
            socket.close()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}