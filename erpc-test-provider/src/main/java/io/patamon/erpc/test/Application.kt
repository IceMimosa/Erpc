package io.patamon.erpc.test

import io.patamon.erpc.spring.configuration.ErpcServerConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Import

/**
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
@SpringBootApplication(scanBasePackages = arrayOf("io.patamon.erpc.test"))
@Import(ErpcServerConfiguration::class)
open class Application {

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(Application::class.java)
            .run(*args)
}