package com.globallogic.jiraapp.domain.dto

import com.atlassian.jira.rest.client.api.domain.Comment
import com.atlassian.jira.rest.client.api.domain.Issue
import org.joda.time.LocalDateTime
import org.springframework.data.annotation.Transient

abstract class AbstractCommentInfo(
        @Transient
        open val author: String?,
        @Transient
        open val dateTime: Long,
        @Transient
        open val text: String
)

data class CommentInfo(
        override val author: String?,
        override val dateTime: Long,
        override val text: String
) : AbstractCommentInfo(author, dateTime, text)

/* */

fun Comment.toCommentInfo(): CommentInfo =
        CommentInfo(
                author?.displayName ?: "",
                updateDate.millis,
                body
        )

fun IssueInfo.toCommentInfo(history: String): CommentInfo =
        CommentInfo(
                assignee,
                LocalDateTime.now().millisOfSecond.toLong(),
                "Update: $history"
        )

fun Issue.getLastComment(): CommentInfo? =
        comments?.maxBy { it.creationDate }?.toCommentInfo()
