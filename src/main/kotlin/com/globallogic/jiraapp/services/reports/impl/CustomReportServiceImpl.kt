package com.globallogic.jiraapp.services.reports.impl

import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.domain.metrics.CustomIssuesMetric
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import com.globallogic.jiraapp.domain.reports.getIssueReportItem
import com.globallogic.jiraapp.repositories.metrics.CustomMetricRepository
import com.globallogic.jiraapp.services.reports.IssuesReportService
import org.springframework.stereotype.Service

import java.util.Collections.emptyList

@Service
class CustomReportServiceImpl(
        private val customsMetricRepository: CustomMetricRepository
) : IssuesReportService<CustomIssuesMetric, String>(customsMetricRepository) {

    override fun createReport(metricId: String, sprintIssues: SprintIssues): List<IssueReportItem> =
            customsMetricRepository.findById(metricId)
                    .map { metric ->
                        metric.getIssues(sprintIssues)
                                .map { it.getIssueReportItem() }
                                .sortedBy { it.issueKey }
                    }
                    .orElse(emptyList())
}
