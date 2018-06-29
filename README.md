RPC 学习

## 1. 基本学习

* 基本类库学习(BIO, NIO, Netty)
  * rpc-io-test
* Dubbo例子
  * rpc-dubbo-provider-api
  * rpc-dubbo-provider
  * rpc-dubbo-consumer
  
  
## 2. 自己封装一个分布式RPC框架

* 核心模块
  * erpc-common:        公用模块
  * erpc-registry:      注册中心
  * erpc-serialization: 序列化和反序列化
  * erpc-codec:         传输数据的编解码器
  * erpc-server:        Server 端
  * erpc-client:        Client 端
* Spring封装
  * erpc-spring-boot-starter: 封装成SpringbootStarter, 并提供 `@RpcProvider` 和 `@RpcConsumer` 注解
  
## 3. 测试自己封装的RPC框架

* erpc-test-provider-api
* erpc-test-provider
* erpc-test-consumer
  
  
## 4. TODO

* [ ] 更加丰富的注册中心, 譬如 `redis`
* [ ] 更加丰富的数据传输协议
* [ ] 当服务端挂掉, 自动从注册中心删去节点
* [ ] 更加丰富的配置, 以及其他
* [x] 放心, 以上我都不会实现。
* [x] Enjoy !!!

