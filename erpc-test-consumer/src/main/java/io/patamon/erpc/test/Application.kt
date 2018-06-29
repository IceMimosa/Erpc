package io.patamon.erpc.test

import io.patamon.erpc.spring.annotation.RpcConsumer
import io.patamon.erpc.spring.configuration.ErpcClientConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Import
import javax.annotation.PostConstruct

/**
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
@SpringBootApplication(scanBasePackages = arrayOf("io.patamon.erpc.test"))
@Import(ErpcClientConfiguration::class)
open class Application {

    @RpcConsumer
    private lateinit var demoErpcService: DemoErpcService

    @PostConstruct
    fun sayHello() {
        val result = demoErpcService.sayHello("Mimosa")
        println(result)
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(Application::class.java)
            .run(*args)
}