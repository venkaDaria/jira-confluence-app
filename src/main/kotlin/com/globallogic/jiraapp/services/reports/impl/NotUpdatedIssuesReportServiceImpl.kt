package com.globallogic.jiraapp.services.reports.impl

import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.domain.metrics.NotUpdatedIssuesMetric
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import com.globallogic.jiraapp.domain.reports.getIssueReportItem
import com.globallogic.jiraapp.repositories.metrics.NotUpdatedIssuesMetricRepository
import com.globallogic.jiraapp.services.reports.IssuesReportService
import org.springframework.stereotype.Service

import java.util.Collections.emptyList

@Service
class NotUpdatedIssuesReportServiceImpl(
        private val notUpdatedIssuesMetricRepository: NotUpdatedIssuesMetricRepository
) : IssuesReportService<NotUpdatedIssuesMetric, JqlMetric>(notUpdatedIssuesMetricRepository) {

    override fun createReport(metricId: String, sprintIssues: SprintIssues): List<IssueReportItem> =
            notUpdatedIssuesMetricRepository.findById(metricId)
                    .map { metric ->
                        metric.getIssues(sprintIssues)
                                .map { it.getIssueReportItem() }
                                .sortedBy { it.issueKey }
                    }
                    .orElse(emptyList())
}
