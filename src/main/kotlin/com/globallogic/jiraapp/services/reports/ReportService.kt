package com.globallogic.jiraapp.services.reports

import com.globallogic.jiraapp.domain.enums.SprintIssues

interface ReportService<T> {

    fun createReport(metricId: String, sprintIssues: SprintIssues): List<T> {
        return emptyList()
    }

    fun createReportWithHistory(metricId: String, sprintIssues: SprintIssues): List<T> {
        return emptyList()
    }
}
