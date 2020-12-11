package com.globallogic.jiraapp.domain.dto

data class JqlMetricDto(
        val id: String?,
        val name: String,
        val rate: String,
        val enabled: Boolean?,
        val custom: Boolean? = false
)
