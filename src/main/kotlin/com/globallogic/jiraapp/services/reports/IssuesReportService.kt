package com.globallogic.jiraapp.services.reports

import com.globallogic.jiraapp.domain.enums.SprintIssues
import com.globallogic.jiraapp.domain.metrics.AbstractIssuesMetric
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import com.globallogic.jiraapp.domain.reports.getIssueReportItemWithHistory
import com.globallogic.jiraapp.repositories.IssuesMetricRepository
import java.util.*

abstract class IssuesReportService<T, R>(
        private val issuesMetricRepository: IssuesMetricRepository<T, R>
) : ReportService<IssueReportItem> where T : AbstractIssuesMetric<R> {

    override fun createReportWithHistory(metricId: String, sprintIssues: SprintIssues): List<IssueReportItem> =
            issuesMetricRepository.findById(metricId)
                    .map { metric ->
                        metric.getIssues(sprintIssues)
                                .map { it.getIssueReportItemWithHistory() }
                                .filter { it.comment?.isNotEmpty() == true }
                                .sortedBy { it.issueKey }
                    }
                    .orElse(Collections.emptyList())
}
