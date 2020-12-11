package com.globallogic.jiraapp.config.dto

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.net.URI

@Component
@ConfigurationProperties(prefix = "jira", ignoreUnknownFields = false)
class JiraProperties {

    lateinit var url: URI
    lateinit var username: String
    lateinit var password: String
    lateinit var project: String
    lateinit var sprintSub: String
    var board: Int = 0
}
