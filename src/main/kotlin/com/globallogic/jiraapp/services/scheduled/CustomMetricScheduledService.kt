package com.globallogic.jiraapp.services.scheduled

import com.globallogic.jiraapp.domain.enums.JqlMetric.ALL_OPEN_PROGRESS_ISSUES
import com.globallogic.jiraapp.domain.enums.JqlMetric.ONLY_CURRENT_SPRINT
import com.globallogic.jiraapp.domain.enums.MetricStatus
import com.globallogic.jiraapp.domain.metrics.CustomIssuesMetric
import com.globallogic.jiraapp.repositories.metrics.CustomMetricRepository
import com.globallogic.jiraapp.services.impl.JqlServiceImpl
import org.springframework.stereotype.Service

@Service
class CustomMetricScheduledService(
        private val jqlServiceImpl: JqlServiceImpl,
        private val customMetricRepository: CustomMetricRepository
) {

    fun simpleTask(name: String, jql: String) {
        val currentIssues = jqlServiceImpl.getIssues(arrayOf(jql, ONLY_CURRENT_SPRINT.jql))
        val otherIssues = jqlServiceImpl.getIssues(arrayOf(jql)) - currentIssues

        val allCount = jqlServiceImpl.getIssuesCount(arrayOf(ALL_OPEN_PROGRESS_ISSUES)).toDouble()
        val percent = (currentIssues.size + otherIssues.size) / allCount

        val status = MetricStatus.YELLOW

        val customIssuesMetric = CustomIssuesMetric(
                metric = name,
                calculationTime = System.currentTimeMillis(),
                result = percent,
                currentIssues = currentIssues,
                otherIssues = otherIssues,
                status = status
        )

        customMetricRepository.save(customIssuesMetric)
    }
}
