package com.globallogic.jiraapp.controllers.reports

import com.globallogic.jiraapp.controllers.metrics.IssuesPerPersonMetricController
import com.globallogic.jiraapp.domain.enums.JqlMetric.UPDATED_ISSUES
import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.services.reports.impl.IssuesPerPersonReportServiceImpl
import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports/issues-per-person")
class IssuesPerPersonReportController(
        private val issuesPerPersonReportService: IssuesPerPersonReportServiceImpl,
        private val personVelocityServiceImpl: AbstractVelocityService
) : Logging by LoggingImpl<IssuesPerPersonMetricController>() {

    @GetMapping("/{id}")
    fun getIssuesPerPersonReportWithHistory(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create an issues per person report for metric table")

        val report = issuesPerPersonReportService.createReportWithHistory(id, sprint)

        return personVelocityServiceImpl.renderTemplate(report, UPDATED_ISSUES.toString(), sprint)
    }
}
