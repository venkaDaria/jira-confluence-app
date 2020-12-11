package com.globallogic.jiraapp.controllers.reports

import com.globallogic.jiraapp.domain.enums.JqlMetric.UPDATED_ISSUES
import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.services.reports.impl.PersonWorklogReportServiceImpl
import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports/person-worklog")
class PersonWorklogReportController(
        private val personWorklogReportService: PersonWorklogReportServiceImpl,
        private val personVelocityServiceImpl: AbstractVelocityService
) : Logging by LoggingImpl<PersonWorklogReportController>() {

    @GetMapping("/{id}")
    fun getPersonWorklogReportWithHistory(@PathVariable id: String, @RequestParam sprint: SprintIssues): String {
        log.debug("Create an issues per person report for metric table")

        val report = personWorklogReportService.createReportWithHistory(id, sprint)

        return personVelocityServiceImpl.renderTemplate(report, UPDATED_ISSUES.toString(), sprint)
    }
}
