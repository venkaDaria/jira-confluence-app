package com.globallogic.jiraapp.domain.dto

data class JqlUpdateMetricDto(
        val name: String,
        val rate: String,
        val enabled: Boolean,
        val custom: Boolean
)
