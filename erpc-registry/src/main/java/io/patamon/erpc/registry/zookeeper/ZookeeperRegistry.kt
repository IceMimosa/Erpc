package io.patamon.erpc.registry.zookeeper

import io.patamon.erpc.registry.Registry
import org.I0Itec.zkclient.ZkClient

/**
 * Desc: 注册中心 zookeeper 实现
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
class ZookeeperRegistry(
        // zookeeper 地址
        zkAddress: String = "127.0.0.1:2181"
) : Registry {

    /** zk 根目录 */
    private val ROOT = "erpc"
    /** 初始化 zkClient */
    private val zkClient: ZkClient = ZkClient(zkAddress)

    /**
     * 向注册中心注册服务
     *
     * [serviceName]:   服务接口的全名名称
     * [serverAddress]: 服务提供者的地址
     */
    override fun regist(serviceName: String, serverAddress: String): Boolean {
        // 创建服务根节点
        create("/$ROOT/$serviceName")
        // 创建主机节点
        return create("/$ROOT/$serviceName/$serverAddress", false)
    }

    /**
     * 服务发现, 返回服务提供者的地址
     */
    override fun discover(serviceName: String): String {
        val paths = zkClient.getChildren("/$ROOT/$serviceName")
        if (paths.isEmpty()) {
            throw RuntimeException("No regist server found !!!")
        }
        // TODO, 默认返回第一个, 需要做负载均衡
        return paths[0]
    }

    /**
     * 关闭服务注册对象
     */
    override fun close() {
        zkClient.close()
    }

    /**
     * 递归创建 zk path
     */
    private fun create(path: String, persist: Boolean = true): Boolean {
        if (path.isBlank()) {
            return false
        }
        var pathLine = StringBuilder()
        path.split("/").filter { it.isNotBlank() }.forEach {
            pathLine = pathLine.append("/$it")
            if (!zkClient.exists(pathLine.toString())) {
                if (persist) {
                    zkClient.createPersistent(pathLine.toString())
                } else {
                    zkClient.createEphemeral(pathLine.toString())
                }
            }
        }
        return true
    }
}