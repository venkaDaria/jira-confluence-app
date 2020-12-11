package com.globallogic.jiraapp.services.scheduled

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@RefreshScope
@Service
@ConditionalOnProperty(value = ["metric.metrics.issues_per_person.enabled"], havingValue = "true")
class IssuesPerPersonMetricScheduledService(
        private val issuesPerPersonMetricServiceForScheduling: IssuesPerPersonMetricServiceForScheduling
) {

    @Scheduled(fixedRateString = "\${metric.metrics.issues_per_person.rate}")
    fun scheduleFixedDelayTask() = issuesPerPersonMetricServiceForScheduling.calculateIssuesPerPerson()
}
