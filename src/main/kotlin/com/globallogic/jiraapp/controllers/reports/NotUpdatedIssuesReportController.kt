package com.globallogic.jiraapp.controllers.reports

import com.globallogic.jiraapp.domain.enums.JqlMetric.NOT_UPDATED_ISSUES
import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.services.reports.impl.NotUpdatedIssuesReportServiceImpl
import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports/not-updated-issues")
class NotUpdatedIssuesReportController(
        private val notUpdatedIssuesReportService: NotUpdatedIssuesReportServiceImpl,
        private val fullVelocityServiceImpl: AbstractVelocityService
) : Logging by LoggingImpl<NotUpdatedIssuesReportController>() {

    @GetMapping("/{id}")
    fun getNotUpdatedIssuesReport(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create a not-updated report for metric table")

        val report = notUpdatedIssuesReportService.createReport(id, sprint)

        return fullVelocityServiceImpl.renderTemplate(report, NOT_UPDATED_ISSUES.toString(), sprint)
    }

    @GetMapping("/{id}/mode2")
    fun getNotUpdatedIssuesReportWithHistory(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create a not-updated report for metric table")

        val report = notUpdatedIssuesReportService.createReportWithHistory(id, sprint)

        return fullVelocityServiceImpl.renderTemplate(report, NOT_UPDATED_ISSUES.toString(), sprint)
    }
}
