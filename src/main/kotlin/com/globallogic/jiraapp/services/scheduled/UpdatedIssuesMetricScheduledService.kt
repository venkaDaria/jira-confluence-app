package com.globallogic.jiraapp.services.scheduled

import com.globallogic.jiraapp.config.dto.MetricProperties
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric.UPDATED_ISSUES
import com.globallogic.jiraapp.domain.enums.MetricStatus
import com.globallogic.jiraapp.domain.metrics.UpdatedIssuesMetric
import com.globallogic.jiraapp.repositories.metrics.UpdatedIssuesMetricRepository
import com.globallogic.jiraapp.services.impl.JqlServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis

@RefreshScope
@Service
@ConditionalOnProperty(value = ["metric.metrics.updated_issues.enabled"], havingValue = "true")
class UpdatedIssuesMetricScheduledService(
        private val metricProperties: MetricProperties,
        jqlServiceImpl: JqlServiceImpl,
        private val updatedIssuesMetricRepository: UpdatedIssuesMetricRepository
) : AbstractIssuesMetricScheduledService(jqlServiceImpl) {

    @Scheduled(fixedRateString = "\${metric.metrics.updated_issues.rate}")
    fun scheduleFixedDelayTask() =
            simpleDelayTask(UPDATED_ISSUES) { metricProperties.getStatusForGoodIssues(it) }

    override fun saveMetric(currentIssues: List<IssueInfo>, otherIssues: List<IssueInfo>,
                            percent: Double, status: MetricStatus) {
        val updatedIssuesMetric = UpdatedIssuesMetric(
                calculationTime = currentTimeMillis(),
                result = percent,
                currentIssues = currentIssues,
                otherIssues = otherIssues,
                status = status
        )

        updatedIssuesMetricRepository.save(updatedIssuesMetric)
    }
}
