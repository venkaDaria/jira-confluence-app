package com.globallogic.jiraapp.repositories.metrics

import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.metrics.NotUpdatedIssuesMetric
import com.globallogic.jiraapp.repositories.IssuesMetricRepository

interface NotUpdatedIssuesMetricRepository : IssuesMetricRepository<NotUpdatedIssuesMetric, JqlMetric>
