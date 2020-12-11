package com.globallogic.jiraapp.config.dto

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "confluence", ignoreUnknownFields = false)
class ConfluenceProperties {

    var parent: Long = 0
    lateinit var space: String
    lateinit var url: String
    lateinit var username: String
    lateinit var password: String
}
