package io.patamon.erpc.spring.resolver

import io.patamon.erpc.server.netty.NettyServer
import io.patamon.erpc.spring.annotation.RpcProvider
import org.springframework.aop.SpringProxy
import org.springframework.aop.support.AopUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Primary
import org.springframework.util.SocketUtils
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Desc: rpc provider 注解处理
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
class ErpcProviderProcessor : ApplicationContextAware {

    /**
     * spring application context
     */
    private lateinit var applicationContext: ApplicationContext

    /**
     * erpc netty 服务
     */
    private lateinit var server: NettyServer

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
        Runtime.getRuntime().addShutdownHook(Thread { destroy() })
    }

    @PostConstruct
    fun publishService() {
        server = NettyServer("localhost", SocketUtils.findAvailableTcpPort())

        // 1. 找出所有的 provider
        applicationContext.getBeansWithAnnotation(RpcProvider::class.java).forEach { name, bean ->
            // val rpcProvider = applicationContext.findAnnotationOnBean(name, RpcProvider::class.java)
            val interfaceTypes = realTargetClass(bean).interfaces
            if (interfaceTypes.isEmpty()) {
                throw IllegalStateException("Fail to find class[${bean.javaClass.name}] interface !!!")
            }
            // 如果存在多个实现, primary 实现
            var actualbean = bean
            val beans = applicationContext.getBeansOfType(interfaceTypes[0])
            if (beans.size > 1) {
                actualbean = getPrimaryBean(beans, interfaceTypes[0])
            }
            // 注册服务
            server.publish(interfaceTypes[0].name, actualbean)
        }
        // 2. 启动服务
        server.run()
    }

    /**
     * 获取实际 bean 的类型, 防止被代理
     */
    private fun realTargetClass(bean: Any): Class<*> {
        if (bean is SpringProxy) {
            return AopUtils.getTargetClass(bean)
        }
        return bean::class.java
    }

    /**
     * 获取 [Primary] 的实现
     */
    private fun getPrimaryBean(beans: Map<String, Any> , interfaceType: Class<*>): Any {
        for ((name, bean) in beans) {
            val p = applicationContext.findAnnotationOnBean(name, Primary::class.java)
            if (p != null) {
                return bean
            }
        }
        throw IllegalArgumentException("""multiple bean of type("$interfaceType") has been annotated with @RpcProvider,
            |and no one annotated with @Primary""".trimMargin())
    }

    @PreDestroy
    fun destroy() {
        server.close()
    }
}