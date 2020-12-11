package com.globallogic.jiraapp.config

import com.atlassian.jira.rest.client.api.IssueRestClient
import com.atlassian.jira.rest.client.api.JiraRestClient
import com.atlassian.jira.rest.client.api.SearchRestClient
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.globallogic.jiraapp.config.dto.JiraProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.support.BasicAuthenticationInterceptor
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate

@Configuration
@EnableScheduling
class JiraConfiguration {

    @Bean
    fun jiraRestClientFactory(): AsynchronousJiraRestClientFactory = AsynchronousJiraRestClientFactory()

    @Bean
    fun jiraRestClient(jiraRestClientFactory: AsynchronousJiraRestClientFactory, properties: JiraProperties)
            : JiraRestClient = jiraRestClientFactory.createWithBasicHttpAuthentication(properties.url,
            properties.username, properties.password)

    @Bean
    fun searchRestClient(jiraRestClient: JiraRestClient): SearchRestClient = jiraRestClient.searchClient

    @Bean
    fun issueRestClient(jiraRestClient: JiraRestClient): IssueRestClient = jiraRestClient.issueClient

    @Bean
    fun jiraRestTemplate(jiraAuthorizationInterceptor: ClientHttpRequestInterceptor): RestTemplate =
            RestTemplateBuilder()
                    .messageConverters(MappingJackson2HttpMessageConverter(jacksonObjectMapper()))
                    .additionalInterceptors(jiraAuthorizationInterceptor)
                    .build()

    @Bean
    fun jiraAuthorizationInterceptor(jiraProperties: JiraProperties): ClientHttpRequestInterceptor =
            BasicAuthenticationInterceptor(
                    jiraProperties.username,
                    jiraProperties.password
            )
}
