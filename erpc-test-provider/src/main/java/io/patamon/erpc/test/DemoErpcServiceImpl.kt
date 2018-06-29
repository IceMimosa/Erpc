package io.patamon.erpc.test

/**
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
class DemoErpcServiceImpl : DemoErpcService {

    /**
     * say hello
     */
    override fun sayHello(name: String): String {
        return "RPC: Hello $name"
    }

}