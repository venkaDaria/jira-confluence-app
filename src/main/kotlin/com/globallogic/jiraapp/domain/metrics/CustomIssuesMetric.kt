package com.globallogic.jiraapp.domain.metrics

import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.enums.MetricStatus
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "custom_metrics")
class CustomIssuesMetric(
        override val calculationTime: Long,
        override val metric: String,
        override val result: Double,
        override val status: MetricStatus,
        override val currentIssues: List<IssueInfo>,
        override val otherIssues: List<IssueInfo>,
        override var id: String? = null
) : AbstractIssuesMetric<String>(calculationTime, metric, result, status, currentIssues, otherIssues, id)

