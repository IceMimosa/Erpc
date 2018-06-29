@file:Suppress("UNCHECKED_CAST")

package io.patamon.erpc.client.proxy

import io.patamon.erpc.client.netty.NettyClient
import io.patamon.erpc.common.model.ErpcRequest
import java.lang.reflect.Proxy

/**
 * Desc: 执行类代理
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
object ErpcProxy {

    private val client = NettyClient()

    /**
     * 创建接口代理
     */
    fun <T> createProxyBean(proxyClass: Class<T>): T {
        return Proxy.newProxyInstance(Thread.currentThread().contextClassLoader, arrayOf(proxyClass), { proxy, method, args ->
            var result: Any
            // 1. 处理一些特殊的方法, 如 toString() 等等
            if ("toString" == method.name) {
                return@newProxyInstance method.invoke(proxy, args)
            } // 其他 ...

            // 2. 生成请求
            val request = ErpcRequest(method.declaringClass.name, method.name, method.parameterTypes, args, 1)
            val response = client.call(request)
            // > 处理error, ......

            return@newProxyInstance response.result
        }) as T
    }

}