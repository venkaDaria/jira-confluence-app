package com.globallogic.jiraapp.services.impl

import com.atlassian.jira.rest.client.api.IssueRestClient
import com.atlassian.jira.rest.client.api.IssueRestClient.Expandos.CHANGELOG
import com.atlassian.jira.rest.client.api.SearchRestClient
import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.api.domain.IssueType
import com.atlassian.jira.rest.client.api.domain.SearchResult
import com.globallogic.jiraapp.config.dto.JiraProperties
import com.globallogic.jiraapp.config.dto.MetricProperties
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.dto.getIssueInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import com.globallogic.jiraapp.domain.reports.getIssueReportItemForConfluence
import com.globallogic.jiraapp.services.JqlService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Service
import java.util.Collections.emptySet

const val AND = " AND "
const val STEP = 50

@Service
class JqlServiceImpl(
        private val jiraProperties: JiraProperties,
        private val searchRestClient: SearchRestClient,
        private val issueRestClient: IssueRestClient,
        private val jqlServiceProvider: ObjectProvider<JqlService>,
        private val metricProperties: MetricProperties
) : JqlService, Logging by LoggingImpl<JqlServiceImpl>() {

    override fun searchJql(metrics: Array<out JqlMetric>): SearchResult {
        val totalCount = getIssuesCount(metrics)

        val searchResults = (0..totalCount / STEP)
                .map { jqlServiceProvider.ifAvailable!!.searchJql(it * STEP, STEP, metrics) }
        return SearchResult(0, totalCount, totalCount, searchResults
                .map { it.issues }.flatten())
    }

    override fun getIssues(metrics: Array<out JqlMetric>): List<IssueInfo> {
        val issuesSearchResult = searchJql(metrics)

        return issuesSearchResult.issues
                //.filter { it.issueType.isSubtask || it.issueType.isBug() } // NOTE: only subtasks and bugs?
                .filter { metricProperties.isTrackedPerson(it) }
                .map { getExpandedIssueInfo(it) }
    }

    private fun IssueType.isBug() = this.name == "Bug"

    override fun getIssuesCount(metrics: Array<out JqlMetric>): Int =
            jqlServiceProvider.ifAvailable!!.searchJql(0, 0, metrics).total

    override fun getExpandedIssue(issue: Issue): Issue {
        log.warn("Alert! Jira can fail with: {}", issue.key)
        val claim = issueRestClient.getIssue(issue.key, listOf(CHANGELOG))
                .claim()
        log.warn("OK! Jira returned a result with: {}", issue.key)
        return claim
    }

    override fun getExpandedIssueInfo(issue: Issue): IssueInfo =
            jqlServiceProvider.ifAvailable!!.getExpandedIssue(issue).getIssueInfo()

    override fun getExpandedIssueReportItem(issue: Issue): IssueReportItem =
            jqlServiceProvider.ifAvailable!!.getExpandedIssue(issue).getIssueReportItemForConfluence()

    override fun recoverIssue(e: Throwable, issue: Issue): Issue {
        log.error("Exception was thrown by JIRA: {}", issue.id)
        return issue
    }

    override fun recoverSearchResult(e: Throwable, startAt: Int, maxResults: Int, vararg metrics: JqlMetric)
            : SearchResult {
        log.error("Exception was thrown by JIRA: {}, {}, {}", startAt, maxResults, metrics)
        return SearchResult(startAt, maxResults, 0, emptyList())
    }

    override fun searchJql(
            startAt: Int,
            maxResults: Int,
            vararg metrics: JqlMetric
    ): SearchResult {
        log.warn("Alert! Jira can fail with: {}, {}, {}", startAt, maxResults, metrics)
        val searchResult = searchRestClient.searchJql(buildJql(metrics), maxResults, startAt, emptySet())
                .claim()
        log.warn("OK! Jira returned a result with: {}, {}, {}", startAt, maxResults, metrics)
        return searchResult
    }

    override fun getIssues(metrics: Array<out String>): List<IssueInfo> {
        val issuesSearchResult = searchJql(metrics)

        return issuesSearchResult.issues
                .filter { it.issueType.isSubtask || it.issueType.isBug() } // NOTE: only subtasks and bugs?
                .filter { metricProperties.isTrackedPerson(it) }
                .map { getExpandedIssueInfo(it) }
    }

    override fun getIssuesCount(metrics: Array<out String>): Int =
            jqlServiceProvider.ifAvailable!!.searchJql(0, 0, metrics).total

    override fun searchJql(metrics: Array<out String>): SearchResult {
        val totalCount = getIssuesCount(metrics)

        val searchResults = (0..totalCount / STEP)
                .map { jqlServiceProvider.ifAvailable!!.searchJql(it * STEP, STEP, metrics) }
        return SearchResult(0, totalCount, totalCount, searchResults
                .map { it.issues }.flatten())
    }

    override fun searchJql(
            startAt: Int,
            maxResults: Int,
            vararg metrics: String
    ): SearchResult {
        log.warn("Alert! Jira can fail with: {}, {}, {}", startAt, maxResults, metrics)
        val searchResult = searchRestClient.searchJql(buildJql(metrics), maxResults, startAt, emptySet())
                .claim()
        log.warn("OK! Jira returned a result with: {}, {}, {}", startAt, maxResults, metrics)
        return searchResult
    }

    private fun buildJql(metrics: Array<out JqlMetric>): String =
            "project=${jiraProperties.project}$AND${metrics.joinToString(separator = AND) { it.jql }}"

    private fun buildJql(metrics: Array<out String>): String =
            "project=${jiraProperties.project}$AND${metrics.joinToString(separator = AND) { it }}"
}
