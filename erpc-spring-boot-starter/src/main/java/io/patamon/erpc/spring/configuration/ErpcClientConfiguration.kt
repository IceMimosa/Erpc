package io.patamon.erpc.spring.configuration

import io.patamon.erpc.spring.resolver.ErpcConsumerProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Desc: erpc auto configuration
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
@Configuration
open class ErpcClientConfiguration {

    @Bean
    @ConditionalOnMissingBean(ErpcConsumerProcessor::class)
    open fun erpcConsumerProcessor(): ErpcConsumerProcessor {
        return ErpcConsumerProcessor()
    }

}