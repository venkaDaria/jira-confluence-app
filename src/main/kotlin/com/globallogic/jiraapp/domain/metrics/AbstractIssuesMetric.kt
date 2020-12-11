package com.globallogic.jiraapp.domain.metrics

import com.globallogic.jiraapp.domain.MetricValue
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.enums.MetricStatus
import com.globallogic.jiraapp.domain.enums.SprintIssues
import org.springframework.data.annotation.Transient

abstract class AbstractIssuesMetric<T>(
        @Transient
        override val calculationTime: Long,
        @Transient
        override val metric: T,
        @Transient
        override val result: Double,
        @Transient
        override val status: MetricStatus,
        @Transient
        open val currentIssues: List<IssueInfo>,
        @Transient
        open val otherIssues: List<IssueInfo>,
        @Transient
        override var id: String? = null
) : MetricValue<Double, T>(calculationTime, metric, result, status, id) {

    fun getIssues(sprintIssues: SprintIssues): List<IssueInfo> =
            when (sprintIssues) {
                SprintIssues.CURRENT -> currentIssues
                SprintIssues.NOT_CURRENT -> otherIssues
                SprintIssues.ALL -> currentIssues + otherIssues
            }
}

