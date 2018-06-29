package io.patamon.erpc.spring.annotation

/**
 * Desc: Rpc 服务消费者注解
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class RpcConsumer {

    // 可以有其他属性...

}