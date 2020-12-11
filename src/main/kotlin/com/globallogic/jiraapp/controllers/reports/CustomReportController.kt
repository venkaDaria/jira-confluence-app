package com.globallogic.jiraapp.controllers.reports

import com.globallogic.jiraapp.domain.enums.JqlMetric.NOT_UPDATED_ISSUES
import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.services.reports.impl.CustomReportServiceImpl
import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports/custom")
class CustomReportController(
        private val customReportService: CustomReportServiceImpl,
        private val fullVelocityServiceImpl: AbstractVelocityService
) : Logging by LoggingImpl<CustomReportController>() {

    @GetMapping("/{id}")
    fun getCustomReport(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create a not-updated report for metric table")

        val report = customReportService.createReport(id, sprint)

        return fullVelocityServiceImpl.renderTemplate(report, NOT_UPDATED_ISSUES.toString(), sprint)
    }

    @GetMapping("/{id}/mode2")
    fun getCustomReportWithHistory(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create a not-updated report for metric table")

        val report = customReportService.createReportWithHistory(id, sprint)

        return fullVelocityServiceImpl.renderTemplate(report, NOT_UPDATED_ISSUES.toString(), sprint)
    }
}
