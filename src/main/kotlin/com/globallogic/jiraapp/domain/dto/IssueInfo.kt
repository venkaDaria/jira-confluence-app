package com.globallogic.jiraapp.domain.dto

import com.atlassian.jira.rest.client.api.domain.ChangelogItem
import com.atlassian.jira.rest.client.api.domain.Issue
import com.globallogic.jiraapp.utils.isToday
import org.codehaus.jettison.json.JSONException
import org.codehaus.jettison.json.JSONObject

data class IssueInfo(
        val key: String,
        val lastComment: AbstractCommentInfo?,
        val summary: String,
        val assignee: String?,
        val lastUpdate: Long,
        val type: String,
        val status: String,
        val epic: String?,
        val history: String?,
        val components: List<String>,
        val labels: Set<String>,
        val worklogs: List<WorklogCommentInfo>,
        val timeTrackingInfo: TimeTrackingInfo
)

fun Issue.getIssueInfo(): IssueInfo =
        IssueInfo(
                key = key,
                assignee = assignee?.displayName,
                summary = summary,
                type = issueType.name,
                lastUpdate = updateDate.millis,
                lastComment = getLastComment(),
                worklogs = getWorklogCommentInfos(),
                status = status.name,
                epic = getEpic(),
                history = getLastAction(),
                components = components.map { it.name },
                labels = labels,
                timeTrackingInfo = timeTracking.getTimeTrackingInfo()
        )

fun IssueInfo.getComment(defaultValue: CommentInfo?): String {
    val commentInfos = worklogs.filter { it.isToday() } +
            (lastComment ?: defaultValue)

    return commentInfos
            .joinToString(separator = System.lineSeparator() + System.lineSeparator()) {
                it?.text ?: ""
            }
}

fun Issue.getLastAction(): String =
        changelog
                ?.maxBy { it.created }?.items
                ?.joinToString(separator = System.lineSeparator()) {
                    it.toFormattedString()
                } ?: ""

fun ChangelogItem.toFormattedString(): String =
        String.format("Changed %s from '%s' to '%s'", field, fromString, toString)

private const val EPIC_FIELD = "Epic Link"
private const val PARENT_FIELD = "Parent"

fun Issue.getEpic(): String? =
        getFieldByName(EPIC_FIELD)?.value?.toString() ?: getFieldByName(PARENT_FIELD)?.let { getParent(it.value) }

private fun getParent(parent: Any?): String? =
        if (parent is JSONObject) {
            parent.getFieldByName(EPIC_FIELD)?.toString() ?: parent.getFieldByName(PARENT_FIELD)?.let { getParent(it) }
        } else {
            null
        }

private fun JSONObject.getFieldByName(name: String) = try {
    get(name)
} catch (_: JSONException) {
    null
}
