package com.globallogic.jiraapp.services.scheduled

import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.enums.JqlMetric.ALL_OPEN_PROGRESS_ISSUES
import com.globallogic.jiraapp.domain.enums.JqlMetric.ONLY_CURRENT_SPRINT
import com.globallogic.jiraapp.domain.enums.MetricStatus
import com.globallogic.jiraapp.services.impl.JqlServiceImpl

abstract class AbstractIssuesMetricScheduledService(
        private val jqlServiceImpl: JqlServiceImpl
) {

    protected fun simpleDelayTask(metric: JqlMetric, getStatus: (Double) -> MetricStatus) {
        val currentIssues = jqlServiceImpl.getIssues(arrayOf(metric, ONLY_CURRENT_SPRINT))
        val otherIssues = jqlServiceImpl.getIssues(arrayOf(metric)) - currentIssues

        val allCount = jqlServiceImpl.getIssuesCount(arrayOf(ALL_OPEN_PROGRESS_ISSUES)).toDouble()
        val percent = (currentIssues.size + otherIssues.size) / allCount

        val status = getStatus(percent)

        saveMetric(currentIssues, otherIssues, percent, status)
    }

    protected abstract fun saveMetric(currentIssues: List<IssueInfo>, otherIssues: List<IssueInfo>,
                                      percent: Double, status: MetricStatus)
}
