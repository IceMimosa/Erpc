package io.patamon.erpc.test

import io.patamon.erpc.spring.annotation.RpcProvider
import org.springframework.stereotype.Service

/**
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
@Service
@RpcProvider
class DemoErpcServiceImpl : DemoErpcService {

    /**
     * say hello
     */
    override fun sayHello(name: String): String {
        return "RPC: Hello $name"
    }

}