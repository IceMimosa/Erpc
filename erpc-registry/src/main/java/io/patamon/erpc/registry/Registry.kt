package io.patamon.erpc.registry

/**
 * Desc: 服务注册/发现的接口
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
interface Registry {

    /**
     * 向注册中心注册服务
     *
     * [ServiceName]:   服务接口的全名名称
     * [ServerAddress]: 服务提供者的地址
     */
    fun regist(ServiceName: String, ServerAddress: String): Boolean

    /**
     * 服务发现, 返回服务提供者的地址
     */
    fun discover(ServiceName: String): String
}