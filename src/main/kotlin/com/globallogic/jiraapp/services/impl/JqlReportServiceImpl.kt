package com.globallogic.jiraapp.services.impl

import com.globallogic.jiraapp.config.dto.JiraProperties
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.dto.ReportedWorklog
import com.globallogic.jiraapp.domain.enums.JqlMetric.ONLY_CURRENT_SPRINT
import com.globallogic.jiraapp.domain.jira.Page
import com.globallogic.jiraapp.domain.jira.Sprint
import com.globallogic.jiraapp.services.JqlReportService
import com.globallogic.jiraapp.utils.dateToStringForChart
import com.globallogic.jiraapp.utils.isEqual
import com.globallogic.jiraapp.utils.isYesterday
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.joda.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.lang.String.format

private const val ACTIVE_SPRINT = "%s/rest/agile/latest/board/%d/sprint?state=active"
private const val HOURS_PER_DAY = 8.0
private const val MINUTES_IN_HOUR = 60.0

@Service
class JqlReportServiceImpl(
        private val jiraProperties: JiraProperties,
        private val jqlServiceImpl: JqlServiceImpl,
        private val jiraRestTemplate: RestTemplate
) : JqlReportService, Logging by LoggingImpl<JqlServiceImpl>() {

    private val sprintUrl: String
        get() = format(ACTIVE_SPRINT, jiraProperties.url, jiraProperties.board)

    override fun getReportedWorklog(personCount: Int): ReportedWorklog {
        val issues = jqlServiceImpl.getIssues(arrayOf(ONLY_CURRENT_SPRINT))

        val originalEstimateMinutes = issues
                .map { it.timeTrackingInfo.originalEstimateMinutes }
                .sum()

        val timeSpentMinutes = issues
                .map { it.timeTrackingInfo.timeSpentMinutes }
                .sum()

        val timeSpentMinutesYesterday = issues.getTimeSpentMinutesYesterday()

        val activeSprint = getActiveSprint()
        val dates = activeSprint?.terms

        val expectedPerDaysHours = dates?.let { getExpectedPerDaysHours(originalEstimateMinutes, it) }
                ?: emptyMap()
        val remainingPerDaysHours = dates?.let { getSpentPerDaysHours(issues, originalEstimateMinutes, it) }
                ?: emptyMap()

        return ReportedWorklog(
                expectedSprintHours = originalEstimateMinutes / MINUTES_IN_HOUR,
                actualSprintHours = timeSpentMinutes / MINUTES_IN_HOUR,
                expectedYesterdayHours = personCount * HOURS_PER_DAY,
                actualYesterdayHours = timeSpentMinutesYesterday / MINUTES_IN_HOUR,
                expectedPerDayHours = expectedPerDaysHours,
                remainingPerDayHours = remainingPerDaysHours,
                sprint = activeSprint
        )
    }

    private fun List<IssueInfo>.getTimeSpentMinutesYesterday(): Int =
            map { issueInfo ->
                issueInfo.worklogs
                        .filter { it.isYesterday() }
                        .map { it.timeSpentMinutes }
                        .sum()
            }
                    .sum()

    private fun getExpectedPerDaysHours(originalEstimateMinutes: Int, dates: List<LocalDate>): Map<String, Double> =
            (0 until dates.size)
                    .associateBy(
                            { dates[it].dateToStringForChart() },
                            { (originalEstimateMinutes - originalEstimateMinutes * it / dates.size) / MINUTES_IN_HOUR }
                    )

    private fun getSpentPerDaysHours(issues: List<IssueInfo>, originalEstimateMinutes: Int, dates: List<LocalDate>)
            : Map<String, Double> {
        val spentPerDays = (0 until dates.size)
                .associateBy(
                        { dates[it] },
                        { getSpentForDay(dates[it], issues) }
                )

        val spentPerDaysWithSum = (0 until dates.size)
                .associateBy(
                        { dates[it] },
                        { getSpentSum(spentPerDays, it) }
                )

        return spentPerDaysWithSum.entries
                .associateBy(
                        { it.key.dateToStringForChart() },
                        { (originalEstimateMinutes - it.value) / MINUTES_IN_HOUR }
                )
    }

    private fun getSpentForDay(localDate: LocalDate, issues: List<IssueInfo>): Int =
            issues
                    .map { issue ->
                        issue.worklogs
                                .filter { it.isEqual(localDate) }
                                .map { it.timeSpentMinutes }
                                .sum()
                    }.sum()

    private fun getSpentSum(spentPerDays: Map<LocalDate, Int>, idx: Int): Int =
            spentPerDays.entries
                    .sortedBy { it.key }
                    .take(idx)
                    .map { it.value }
                    .sum()

    private fun getActiveSprint(): Sprint? {
        return jiraRestTemplate
                .getForEntity<Page>(sprintUrl).body
                ?.values
                ?.find { it.name.contains(jiraProperties.sprintSub) }
    }
}
