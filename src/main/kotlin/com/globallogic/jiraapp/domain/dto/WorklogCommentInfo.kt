package com.globallogic.jiraapp.domain.dto

import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.api.domain.Worklog

data class WorklogCommentInfo(
        override val author: String,
        override val dateTime: Long,
        override val text: String,
        val timeSpentMinutes: Int
) : AbstractCommentInfo(author, dateTime, text)

fun Worklog.toWorklogCommentInfo(): WorklogCommentInfo = WorklogCommentInfo(
        timeSpentMinutes = minutesSpent,
        author = author?.displayName ?: "",
        dateTime = updateDate.millis,
        text = comment
)

fun Issue.getWorklogCommentInfos(): List<WorklogCommentInfo> =
        if (issueType.isSubtask)
            worklogs.map { it.toWorklogCommentInfo() }
        else
            emptyList()
