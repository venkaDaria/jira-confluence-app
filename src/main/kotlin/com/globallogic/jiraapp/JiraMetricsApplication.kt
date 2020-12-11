package com.globallogic.jiraapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication
class JiraMetricsApplication

fun main(vararg args: String) {

    @Suppress("SpreadOperator")
    SpringApplication.run(JiraMetricsApplication::class.java, *args)
}
