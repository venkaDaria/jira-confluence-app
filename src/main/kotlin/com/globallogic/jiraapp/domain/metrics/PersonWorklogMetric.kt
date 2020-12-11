package com.globallogic.jiraapp.domain.metrics

import com.globallogic.jiraapp.domain.MetricValue
import com.globallogic.jiraapp.domain.dto.ReportedWorklog
import com.globallogic.jiraapp.domain.dto.UserInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.enums.MetricStatus
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "person_worklog_metrics")
class PersonWorklogMetric(
        override val calculationTime: Long,
        override val result: ReportedWorklog,
        override val status: MetricStatus,
        @field:Transient val userInfos: List<UserInfo>,
        override var id: String? = null
) : MetricValue<ReportedWorklog, JqlMetric>(calculationTime, JqlMetric.ISSUES_PER_PERSON, result, status, id)
