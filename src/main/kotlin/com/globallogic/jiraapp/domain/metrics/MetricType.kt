package com.globallogic.jiraapp.domain.metrics

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "metric_types")
data class MetricType(
        val name: String,
        var rate: String,
        val jql: String,
        var enabled: Boolean = true,
        var deleted: Boolean = false,
        var id: String? = null
)
