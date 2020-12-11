package com.globallogic.jiraapp.controllers

import com.globallogic.jiraapp.domain.enums.SprintIssues.CURRENT
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import com.globallogic.jiraapp.repositories.ReportItemRepository
import com.globallogic.jiraapp.services.scheduled.ReportConfluenceScheduledService
import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.joda.time.LocalDate
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/metrics/confluence")
@ConditionalOnProperty(value = ["metric.reports.published"], havingValue = "true")
class ConfluenceController(
        private val reportItemRepository: ReportItemRepository,
        private val reportConfluenceScheduledService: ReportConfluenceScheduledService,
        private val fullVelocityServiceImpl: AbstractVelocityService
) : Logging by LoggingImpl<ConfluenceController>() {

    @GetMapping
    fun sendConfluenceReport(): String {
        log.debug("REST request to get a confluence report")

        var entries: List<IssueReportItem> = reportItemRepository.findByCalculationDate(LocalDate.now())
        if (entries.isEmpty()) {
            reportConfluenceScheduledService.scheduleFixedDelayTask()
            entries = reportItemRepository.findByCalculationDate(LocalDate.now())
        }

        return fullVelocityServiceImpl.renderTemplate(entries, "Confluence report", CURRENT)
    }
}
