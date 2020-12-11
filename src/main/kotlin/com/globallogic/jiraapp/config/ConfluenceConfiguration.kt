package com.globallogic.jiraapp.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.globallogic.jiraapp.config.dto.ConfluenceProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.support.BasicAuthenticationInterceptor
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class ConfluenceConfiguration {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun confRestTemplate(confAuthorizationInterceptor: ClientHttpRequestInterceptor): RestTemplate =
            RestTemplateBuilder()
                    .messageConverters(MappingJackson2HttpMessageConverter(jacksonObjectMapper()))
                    .additionalInterceptors(confAuthorizationInterceptor)
                    .build()

    @Bean
    fun confAuthorizationInterceptor(confluenceProperties: ConfluenceProperties): ClientHttpRequestInterceptor =
            BasicAuthenticationInterceptor(
                    confluenceProperties.username,
                    confluenceProperties.password
            )
}

