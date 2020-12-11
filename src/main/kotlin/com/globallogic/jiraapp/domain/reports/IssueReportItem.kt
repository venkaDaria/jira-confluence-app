package com.globallogic.jiraapp.domain.reports

import com.atlassian.jira.rest.client.api.domain.Issue
import com.globallogic.jiraapp.domain.dto.*
import com.globallogic.jiraapp.utils.dateToString
import org.joda.time.LocalDate
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "issue_report_items")
data class IssueReportItem(
        val issueKey: String,
        val summary: String,
        val assignee: String?,
        val epic: String?,
        val components: List<String>,
        val labels: Set<String>,
        val updatedDate: String,
        val comment: String?,
        val calculationDate: LocalDate
)

private fun IssueInfo.getIssueReportItem(defaultComment: () -> CommentInfo?): IssueReportItem =
        IssueReportItem(
                issueKey = key,
                assignee = assignee,
                summary = summary,
                epic = epic,
                components = components,
                labels = labels,
                updatedDate = lastUpdate.dateToString(),
                calculationDate = LocalDate.now(),
                comment = getComment(defaultComment())
        )

fun Issue.getIssueReportItemForConfluence(): IssueReportItem =
        getIssueInfo().getIssueReportItemWithHistory()

fun IssueInfo.getIssueReportItem(): IssueReportItem =
        getIssueReportItem {
            null
        }

fun IssueInfo.getIssueReportItemWithHistory(): IssueReportItem =
        getIssueReportItem {
            toCommentInfo(history ?: "")
        }
