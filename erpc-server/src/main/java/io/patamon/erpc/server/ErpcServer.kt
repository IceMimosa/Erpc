package io.patamon.erpc.server

/**
 * Desc: rpc server 端
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
interface ErpcServer {

    /**
     * 运行 server
     */
    fun run(hostname: String, port: Int)

    /**
     * 注册服务实例
     */
    fun publish(bean: Any)
}
