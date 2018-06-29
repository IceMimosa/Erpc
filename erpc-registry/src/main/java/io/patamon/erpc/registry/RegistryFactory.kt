package io.patamon.erpc.registry

import io.patamon.erpc.registry.zookeeper.ZookeeperRegistry

/**
 * Desc: 序列化对象实例加载factory
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
object RegistryFactory {

    /**
     * 默认加载 zookeeper 实现, 这里可以通过配置文件的方式读取, 略......
     */
    fun load() = ZookeeperRegistry()

}