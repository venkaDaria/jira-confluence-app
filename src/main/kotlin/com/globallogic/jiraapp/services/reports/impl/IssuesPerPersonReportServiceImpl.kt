package com.globallogic.jiraapp.services.reports.impl

import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.domain.reports.PersonReportItem
import com.globallogic.jiraapp.domain.reports.getPersonReportItem
import com.globallogic.jiraapp.repositories.metrics.IssuesPerPersonMetricRepository
import com.globallogic.jiraapp.services.reports.ReportService
import org.springframework.stereotype.Service

@Service
class IssuesPerPersonReportServiceImpl(
        private val issuesPerPersonMetricRepository: IssuesPerPersonMetricRepository
) : ReportService<PersonReportItem> {

    override fun createReportWithHistory(metricId: String, sprintIssues: SprintIssues): List<PersonReportItem> =
            issuesPerPersonMetricRepository.findById(metricId)
                    .map { metric ->
                        metric.userInfos
                                .map { getPersonReportItem(it) }
                                .sortedBy { it.displayName }
                    }
                    .orElse(emptyList())
}
