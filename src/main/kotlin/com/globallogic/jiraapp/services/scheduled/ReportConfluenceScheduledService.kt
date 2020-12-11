package com.globallogic.jiraapp.services.scheduled

import com.globallogic.jiraapp.config.dto.MetricProperties
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.enums.JqlMetric.ALL_OPEN_PROGRESS_ISSUES
import com.globallogic.jiraapp.domain.enums.JqlMetric.ONLY_CURRENT_SPRINT
import com.globallogic.jiraapp.domain.enums.SprintIssues.CURRENT
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import com.globallogic.jiraapp.repositories.ReportItemRepository
import com.globallogic.jiraapp.services.ConfluenceService
import com.globallogic.jiraapp.services.JqlService
import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import com.globallogic.jiraapp.utils.dateToString
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.joda.time.LocalDate
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@RefreshScope
@Service
@ConditionalOnProperty(value = ["metric.reports.published"], havingValue = "true")
class ReportConfluenceScheduledService(
        private val jqlService: JqlService,
        private val reportItemRepository: ReportItemRepository,
        private val simpleVelocityServiceImpl: AbstractVelocityService,
        private val confluenceService: ConfluenceService,
        private val metricProperties: MetricProperties
) : Logging by LoggingImpl<ReportConfluenceScheduledService>() {

    @Scheduled(fixedRateString = "\${metric.reports.rate}")
    fun scheduleFixedDelayTask() {
        log.info("Daily report")

        val entries = createReport(ALL_OPEN_PROGRESS_ISSUES, ONLY_CURRENT_SPRINT)
        reportItemRepository.saveAll(entries)

        val pageTitle = getReportName(LocalDate.now())
        val content = simpleVelocityServiceImpl.renderTemplate(entries, pageTitle, CURRENT)

        confluenceService.createPage(pageTitle, content)
    }

    private fun getReportName(date: LocalDate): String {
        return "Report for " + date.dateToString()
    }

    private fun createReport(vararg metrics: JqlMetric): List<IssueReportItem> {
        val searchResult = jqlService.searchJql(metrics)

        return searchResult.issues
                .filter { metricProperties.isTrackedPerson(it) }
                .map { jqlService.getExpandedIssueReportItem(it) }
                .sortedBy { it.issueKey }
    }
}
