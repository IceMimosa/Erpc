package io.patamon.erpc.spring.annotation

/**
 * Desc: Rpc 服务提供者注解
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class RpcProvider {

    // 可以有其他属性...

}