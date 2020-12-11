package com.globallogic.jiraapp.repositories.metrics

import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.metrics.UpdatedIssuesMetric
import com.globallogic.jiraapp.repositories.IssuesMetricRepository

interface UpdatedIssuesMetricRepository : IssuesMetricRepository<UpdatedIssuesMetric, JqlMetric>
