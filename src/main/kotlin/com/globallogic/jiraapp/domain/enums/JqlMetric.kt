package com.globallogic.jiraapp.domain.enums

enum class JqlMetric(val jql: String, val isShown: Boolean) {

    /* Scheduled */

    // also issues without subtasks
    NOT_UPDATED_ISSUES("status in ('To Do', 'In Progress') AND updated <= -10h", true),
    UPDATED_ISSUES("status in ('To Do', 'In Progress') AND updated > -10h", true),

    // also assignee in ()
    ISSUES_PER_PERSON("sprint in openSprints()", true),
    // just for list
    PERSON_WORKLOG("***", true),

    /* Utils */
    ALL_OPEN_PROGRESS_ISSUES("status in ('To Do', 'In Progress')", false),

    ONLY_CURRENT_SPRINT("sprint in openSprints()", false)
}
