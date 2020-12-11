package com.globallogic.jiraapp.domain.enums

import com.atlassian.jira.rest.client.api.domain.Status

enum class IssueStatus(val statusName: String) {

    ALL("All"),
    TO_DO("To Do"),
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    IN_TESTING("In Testing"),
    DELIVERED("Delivered"),
    READY_FOR_DEMO("Ready for Demo"),
    CLOSED("Closed");
}

fun Status.toIssueStatus(): IssueStatus =
        IssueStatus.values()
                .find { it.statusName == name }
                ?: IssueStatus.ALL
