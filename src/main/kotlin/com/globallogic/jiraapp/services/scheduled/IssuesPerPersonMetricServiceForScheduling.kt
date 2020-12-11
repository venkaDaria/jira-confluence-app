package com.globallogic.jiraapp.services.scheduled

import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.api.domain.User
import com.globallogic.jiraapp.config.dto.MetricProperties
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.dto.UserInfo
import com.globallogic.jiraapp.domain.dto.getIssueInfo
import com.globallogic.jiraapp.domain.enums.IssueStatus.ALL
import com.globallogic.jiraapp.domain.enums.JqlMetric.ISSUES_PER_PERSON
import com.globallogic.jiraapp.domain.enums.MetricStatus.RED
import com.globallogic.jiraapp.domain.enums.toIssueStatus
import com.globallogic.jiraapp.domain.metrics.IssuesPerPersonMetric
import com.globallogic.jiraapp.repositories.metrics.IssuesPerPersonMetricRepository
import com.globallogic.jiraapp.services.impl.JqlServiceImpl
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis

@Service
class IssuesPerPersonMetricServiceForScheduling(
        private val issuesPerPersonMetricRepository: IssuesPerPersonMetricRepository,
        private val metricProperties: MetricProperties,
        private val jqlServiceImpl: JqlServiceImpl
) : Logging by LoggingImpl<IssuesPerPersonMetricServiceForScheduling>() {

    fun calculateIssuesPerPerson(): List<UserInfo> {
        val issuesSearchResult = jqlServiceImpl.searchJql(arrayOf(ISSUES_PER_PERSON))

        val filteredIssues = issuesSearchResult.issues
                .filter { metricProperties.isTrackedPerson(it) }
                .map { jqlServiceImpl.getExpandedIssue(it) }

        val userInfos = getIssues(filteredIssues)

        val result = getIssuesCountByStatus(filteredIssues)
        result[ALL.statusName] = filteredIssues.count()

        val status = metricProperties.getMaxStatusForIssuesPerPerson(userInfos)

        val issuesPerPersonMetric = IssuesPerPersonMetric(
                calculationTime = currentTimeMillis(),
                result = result,
                userInfos = userInfos,
                status = status
        )

        issuesPerPersonMetricRepository.save(issuesPerPersonMetric)

        return userInfos
    }

    private fun getIssuesCountByStatus(issues: List<Issue>): MutableMap<String, Int> =
            issues.groupBy { it.status.toIssueStatus().statusName }
                    .mapValues { it.value.size }
                    .toMutableMap()

    private fun getIssues(issues: List<Issue>): List<UserInfo> {
        val userWorklogs = issues
                .flatMap { it.worklogs }
                .groupBy { it.author.displayName }
                .mapValues { it.value.map { w -> w.minutesSpent }.count() }

        val userInfos = issues
                .groupBy { it.assignee }
                .mapValues { it.value.map { issue -> issue.getIssueInfo() } }
                .map { buildUserInfo(userWorklogs[it.key?.displayName], it) }
                .toMutableList()

        userInfos.addAll(metricProperties.people.map { buildUserInfo(it) })

        return userInfos
                .sortedBy { it.status.importance }
                .reversed()
    }

    private fun buildUserInfo(timeReported: Int?, entry: Map.Entry<User?, List<IssueInfo>>): UserInfo {
        return UserInfo(
                login = entry.key?.name ?: "",
                displayName = entry.key?.displayName ?: "",
                issues = entry.value,
                status = metricProperties.getStatusForIssuesPerPerson(entry.value),
                hoursSpent = timeReported ?: 0
        )
    }

    private fun buildUserInfo(person: String): UserInfo {
        return UserInfo(
                login = "",
                displayName = person,
                issues = emptyList(),
                status = RED,
                hoursSpent = 0
        )
    }
}
