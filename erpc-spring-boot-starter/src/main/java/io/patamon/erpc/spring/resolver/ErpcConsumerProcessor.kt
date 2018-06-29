package io.patamon.erpc.spring.resolver

import io.patamon.erpc.client.proxy.ErpcProxy
import io.patamon.erpc.spring.annotation.RpcConsumer
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.BeanCreationException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.util.ReflectionUtils

/**
 * Desc: rpc consumer 注解处理
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
class ErpcConsumerProcessor : BeanPostProcessor, ApplicationContextAware {

    /**
     * spring application context
     */
    private lateinit var applicationContext: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    /**
     * bean 处理之前
     */
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        val clazz = if (AopUtils.isAopProxy(beanName)) AopUtils.getTargetClass(bean) else bean.javaClass
        ReflectionUtils.doWithFields(clazz, { field ->
            val rpcConsumer = field.getAnnotation(RpcConsumer::class.java)
            if (rpcConsumer != null) {
                val serviceClass = field.type
                // 从 spring 中获取
                var obj = try {
                    applicationContext.getBean(serviceClass)
                } catch (e: Exception) {
                    // ignore
                    null
                }
                // 否则代理获取
                if (obj == null) {
                    obj = ErpcProxy.createProxyBean(serviceClass)
                }
                try {
                    field.isAccessible = true
                    field.set(bean, obj)
                    field.isAccessible = false
                } catch (e: Exception) {
                    throw BeanCreationException(beanName, e)
                }
            }
        })
        return bean
    }

    /**
     * bean 处理之后
     */
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        return bean
    }

}