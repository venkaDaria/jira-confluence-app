package com.globallogic.jiraapp.domain.metrics

import com.globallogic.jiraapp.domain.MetricValue
import com.globallogic.jiraapp.domain.dto.UserInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.enums.MetricStatus
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "issues_per_person_metrics")
data class IssuesPerPersonMetric(
        override val calculationTime: Long,
        override val result: MutableMap<String, Int>,
        override val status: MetricStatus,
        val userInfos: List<UserInfo>,
        override var id: String? = null
) : MetricValue<MutableMap<String, Int>, JqlMetric>(calculationTime, JqlMetric.ISSUES_PER_PERSON, result, status, id)
