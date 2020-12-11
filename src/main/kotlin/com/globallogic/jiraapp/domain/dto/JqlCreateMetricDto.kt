package com.globallogic.jiraapp.domain.dto

data class JqlCreateMetricDto(
        val name: String,
        val rate: String,
        val jql: String
)
