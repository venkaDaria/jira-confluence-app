package com.globallogic.jiraapp.controllers.reports

import com.globallogic.jiraapp.domain.enums.JqlMetric.UPDATED_ISSUES
import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.services.reports.impl.UpdatedIssuesReportServiceImpl
import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports/updated-issues")
class UpdatedIssuesReportController(
        private val updatedIssuesReportService: UpdatedIssuesReportServiceImpl,
        private val fullVelocityServiceImpl: AbstractVelocityService
) : Logging by LoggingImpl<UpdatedIssuesReportController>() {

    @GetMapping("/{id}")
    fun getUpdatedIssuesReport(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create an updated report for metric table")

        val report = updatedIssuesReportService.createReport(id, sprint)

        return fullVelocityServiceImpl.renderTemplate(report, UPDATED_ISSUES.toString(), sprint)
    }

    @GetMapping("/{id}/mode2")
    fun getUpdatedIssuesReportWithHistory(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create an updated report for metric table")

        val report = updatedIssuesReportService.createReportWithHistory(id, sprint)

        return fullVelocityServiceImpl.renderTemplate(report, UPDATED_ISSUES.toString(), sprint)
    }
}
