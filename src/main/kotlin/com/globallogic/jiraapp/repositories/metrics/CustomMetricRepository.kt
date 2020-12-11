package com.globallogic.jiraapp.repositories.metrics

import com.globallogic.jiraapp.domain.metrics.CustomIssuesMetric
import com.globallogic.jiraapp.repositories.IssuesMetricRepository

interface CustomMetricRepository : IssuesMetricRepository<CustomIssuesMetric, String> {

    fun findAllByMetric(name: String): List<CustomIssuesMetric>
}
