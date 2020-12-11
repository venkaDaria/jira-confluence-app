package com.globallogic.jiraapp.services.scheduled

import com.globallogic.jiraapp.config.dto.MetricProperties
import com.globallogic.jiraapp.domain.metrics.PersonWorklogMetric
import com.globallogic.jiraapp.repositories.metrics.PersonWorklogMetricRepository
import com.globallogic.jiraapp.services.impl.JqlReportServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

import java.lang.System.currentTimeMillis

@RefreshScope
@Service
@ConditionalOnProperty(value = ["metric.metrics.person_worklog.enabled"], havingValue = "true")
class PersonWorklogMetricScheduledService(
        private val personWorklogMetricRepository: PersonWorklogMetricRepository,
        private val issuesPerPersonMetricServiceForScheduling: IssuesPerPersonMetricServiceForScheduling,
        private val metricProperties: MetricProperties,
        private val jqlReportServiceImpl: JqlReportServiceImpl
) {

    @Scheduled(fixedRateString = "\${metric.metrics.person_worklog.rate}")
    fun scheduleFixedDelayTask() {
        val userInfos = issuesPerPersonMetricServiceForScheduling.calculateIssuesPerPerson()

        val result = jqlReportServiceImpl.getReportedWorklog(metricProperties.people.size)

        val sprintDuration = result.sprint?.terms?.size ?: 0

        val userInfosWithNewStatus = userInfos.map {
            it.withStatus { hours -> metricProperties.getStatusForWorklogPerson(sprintDuration, hours) }
        }

        val status = metricProperties.getStatusForWorklog(result)

        val personWorklogMetric = PersonWorklogMetric(
                calculationTime = currentTimeMillis(),
                result = result,
                userInfos = userInfosWithNewStatus,
                status = status
        )

        personWorklogMetricRepository.save(personWorklogMetric)
    }
}
