package com.globallogic.jiraapp.services.scheduled

import com.globallogic.jiraapp.config.dto.MetricProperties
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric.NOT_UPDATED_ISSUES
import com.globallogic.jiraapp.domain.enums.MetricStatus
import com.globallogic.jiraapp.domain.enums.MetricStatus.GREEN
import com.globallogic.jiraapp.domain.metrics.NotUpdatedIssuesMetric
import com.globallogic.jiraapp.repositories.metrics.NotUpdatedIssuesMetricRepository
import com.globallogic.jiraapp.services.impl.JqlServiceImpl
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis

@RefreshScope
@Service
@ConditionalOnProperty(value = ["metric.metrics.not_updated_issues.enabled"], havingValue = "true")
class NotUpdatedIssuesMetricScheduledService(
        private val metricProperties: MetricProperties,
        jqlServiceImpl: JqlServiceImpl,
        private val notUpdatedIssuesMetricRepository: NotUpdatedIssuesMetricRepository
) : AbstractIssuesMetricScheduledService(jqlServiceImpl),
        Logging by LoggingImpl<NotUpdatedIssuesMetricScheduledService>() {

    @Scheduled(fixedRateString = "\${metric.metrics.not_updated_issues.rate}")
    fun scheduleFixedDelayTask() =
            simpleDelayTask(NOT_UPDATED_ISSUES) { metricProperties.getStatusForBadIssues(it) }

    override fun saveMetric(currentIssues: List<IssueInfo>, otherIssues: List<IssueInfo>,
                            percent: Double, status: MetricStatus) {
        if (status == GREEN) {
            return
        }

        val notUpdatedIssuesMetric = NotUpdatedIssuesMetric(
                calculationTime = currentTimeMillis(),
                result = percent,
                currentIssues = currentIssues,
                otherIssues = otherIssues,
                status = status
        )

        notUpdatedIssuesMetricRepository.save(notUpdatedIssuesMetric)
    }
}
