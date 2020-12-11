package com.globallogic.jiraapp.services.reports.impl

import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.domain.metrics.UpdatedIssuesMetric
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import com.globallogic.jiraapp.domain.reports.getIssueReportItem
import com.globallogic.jiraapp.repositories.metrics.UpdatedIssuesMetricRepository
import com.globallogic.jiraapp.services.reports.IssuesReportService
import org.springframework.stereotype.Service

import java.util.Collections.emptyList

@Service
class UpdatedIssuesReportServiceImpl(
        private val updatedIssuesMetricRepository: UpdatedIssuesMetricRepository
) : IssuesReportService<UpdatedIssuesMetric, JqlMetric>(updatedIssuesMetricRepository) {

    override fun createReport(metricId: String, sprintIssues: SprintIssues): List<IssueReportItem> =
            updatedIssuesMetricRepository.findById(metricId)
                    .map { metric ->
                        metric.getIssues(sprintIssues)
                                .map { it.getIssueReportItem() }
                                .sortedBy { it.issueKey }
                    }
                    .orElse(emptyList())

}
