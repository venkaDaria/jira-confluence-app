package com.globallogic.jiraapp.config.dto

import com.atlassian.jira.rest.client.api.domain.Issue
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.dto.ReportedWorklog
import com.globallogic.jiraapp.domain.dto.UserInfo
import com.globallogic.jiraapp.domain.enums.IssueStatus.IN_PROGRESS
import com.globallogic.jiraapp.domain.enums.MetricStatus
import com.globallogic.jiraapp.domain.enums.MetricStatus.*
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

private const val HOURS_PER_DAY = 8

@Component
@ConfigurationProperties(prefix = "metric", ignoreUnknownFields = false)
class MetricProperties {

    lateinit var people: Set<String>

    val metrics: Map<String, MetricPropertiesDto> = mutableMapOf()

    val badIssuesCount: DoubleMetricStatusDto = DoubleMetricStatusDto()

    val goodIssuesCount: DoubleMetricStatusDto = DoubleMetricStatusDto()

    val maxIssuesCountPerSprint: IntMetricStatusDto = IntMetricStatusDto()

    val reportedWorklogDifference: DoubleMetricStatusDto = DoubleMetricStatusDto()

    val reports: ReportPropertiesDto = ReportPropertiesDto()

    fun getMetric(name: String): MetricPropertiesDto? =
            metrics[name.toLowerCase()]

    fun isTrackedPerson(issue: Issue): Boolean =
            people.contains(issue.assignee?.displayName)

    private fun <T> getStatus(count: Comparable<T>, thresholds: MetricStatusDto<T>): MetricStatus =
            when {
                count >= thresholds.redThreshold -> RED
                count >= thresholds.yellowThreshold -> YELLOW
                else -> GREEN
            }

    private fun <T> getStatusOpposite(count: Comparable<T>, thresholds: MetricStatusDto<T>): MetricStatus =
            when {
                count <= thresholds.redThreshold -> RED
                count <= thresholds.yellowThreshold -> YELLOW
                else -> GREEN
            }

    fun getStatusForBadIssues(percent: Double): MetricStatus =
            getStatus(percent, badIssuesCount)

    fun getStatusForGoodIssues(percent: Double): MetricStatus =
            getStatusOpposite(percent, goodIssuesCount)

    fun getStatusForWorklogPerson(sprintDuration: Int, result: Int): MetricStatus {
        val metricStatusDto = IntMetricStatusDto()
        metricStatusDto.redThreshold = (sprintDuration - 4) * HOURS_PER_DAY
        metricStatusDto.yellowThreshold = (sprintDuration - 2) * HOURS_PER_DAY

        return getStatusOpposite(result, metricStatusDto)
    }

    // NOTE: without getStatus(result.expectedYesterdayHours - result.actualYesterdayHours, reportedWorklogDifference)
    fun getStatusForWorklog(result: ReportedWorklog): MetricStatus =
            getStatus(result.expectedSprintHours - result.actualSprintHours, reportedWorklogDifference)

    fun getStatusForIssuesPerPerson(issueInfos: List<IssueInfo>): MetricStatus =
            getStatusForIssuesPerPerson(countInProgressIssues(issueInfos))

    fun getMaxStatusForIssuesPerPerson(userInfos: List<UserInfo>): MetricStatus =
            userInfos
                    .map { countInProgressIssues(it.issues) }
                    .map { getStatusForIssuesPerPerson(it) }
                    .maxBy { it.importance } ?: GREEN

    private fun getStatusForIssuesPerPerson(count: Int): MetricStatus =
            if (count == 0) RED else getStatus(count, maxIssuesCountPerSprint)

    private fun countInProgressIssues(issueInfos: List<IssueInfo>): Int =
            issueInfos
                    .filter { it.status == IN_PROGRESS.statusName }
                    .count()
}
