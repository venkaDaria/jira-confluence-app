package com.globallogic.jiraapp.domain.metrics

import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.enums.MetricStatus
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "updated_issues_metrics")
class UpdatedIssuesMetric(
        override val calculationTime: Long,
        override val result: Double,
        override val status: MetricStatus,
        override val currentIssues: List<IssueInfo>,
        override val otherIssues: List<IssueInfo>,
        override var id: String? = null
) : AbstractIssuesMetric<JqlMetric>(calculationTime, JqlMetric.UPDATED_ISSUES, result, status, currentIssues, otherIssues, id)
