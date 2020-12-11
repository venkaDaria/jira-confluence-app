package com.globallogic.jiraapp.services.reports.impl

import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.domain.reports.PersonReportItem
import com.globallogic.jiraapp.domain.reports.getPersonReportItem
import com.globallogic.jiraapp.repositories.metrics.PersonWorklogMetricRepository
import com.globallogic.jiraapp.services.reports.ReportService
import org.springframework.stereotype.Service

import java.util.Collections.emptyList

@Service
class PersonWorklogReportServiceImpl(
        private val personWorklogMetricRepository: PersonWorklogMetricRepository
) : ReportService<PersonReportItem> {

    override fun createReportWithHistory(metricId: String, sprintIssues: SprintIssues): List<PersonReportItem> =
            personWorklogMetricRepository.findById(metricId)
                    .map { metric ->
                        metric.userInfos
                                .map { getPersonReportItem(it) }
                                .sortedBy { it.displayName }
                    }
                    .orElse(emptyList())
}
