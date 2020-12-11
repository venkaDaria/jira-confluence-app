package com.globallogic.jiraapp.services

import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.api.domain.SearchResult
import com.globallogic.jiraapp.domain.dto.IssueInfo
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.reports.IssueReportItem
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable

private const val MAX_ATTEMPTS = 10

interface JqlService {

    fun searchJql(metrics: Array<out JqlMetric>): SearchResult

    fun getIssues(metrics: Array<out JqlMetric>): List<IssueInfo>

    fun getIssuesCount(metrics: Array<out JqlMetric>): Int

    @Retryable(maxAttempts = MAX_ATTEMPTS)
    fun getExpandedIssue(issue: Issue): Issue

    @Recover
    fun recoverIssue(e: Throwable, issue: Issue): Issue

    fun getExpandedIssueInfo(issue: Issue): IssueInfo

    fun getExpandedIssueReportItem(issue: Issue): IssueReportItem

    @Retryable(maxAttempts = MAX_ATTEMPTS)
    fun searchJql(startAt: Int, maxResults: Int, metrics: Array<out JqlMetric>): SearchResult

    @Retryable(maxAttempts = MAX_ATTEMPTS)
    fun searchJql(startAt: Int, maxResults: Int, metrics: Array<out String>): SearchResult

    @Recover
    fun recoverSearchResult(e: Throwable, startAt: Int, maxResults: Int, metrics: Array<out JqlMetric>): SearchResult

    fun getIssues(metrics: Array<out String>): List<IssueInfo>
    fun searchJql(metrics: Array<out String>): SearchResult
    fun getIssuesCount(metrics: Array<out String>): Int
}
