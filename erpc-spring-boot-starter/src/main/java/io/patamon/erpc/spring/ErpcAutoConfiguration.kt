package io.patamon.erpc.spring

import io.patamon.erpc.spring.resolver.ErpcConsumerProcessor
import io.patamon.erpc.spring.resolver.ErpcProviderProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Desc: erpc auto configuration
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
@Configuration
@ComponentScan("io.patamon.erpc.spring")
open class ErpcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ErpcProviderProcessor::class)
    open fun erpcProviderProcessor(): ErpcProviderProcessor {
        return ErpcProviderProcessor()
    }

    @Bean
    @ConditionalOnMissingBean(ErpcConsumerProcessor::class)
    open fun erpcConsumerProcessor(): ErpcConsumerProcessor {
        return ErpcConsumerProcessor()
    }

}