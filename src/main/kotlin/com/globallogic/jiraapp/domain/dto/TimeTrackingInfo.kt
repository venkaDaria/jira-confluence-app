package com.globallogic.jiraapp.domain.dto

import com.atlassian.jira.rest.client.api.domain.TimeTracking

data class TimeTrackingInfo(
        val originalEstimateMinutes: Int,
        val remainingEstimateMinutes: Int,
        val timeSpentMinutes: Int
)

fun TimeTracking?.getTimeTrackingInfo(): TimeTrackingInfo =
        TimeTrackingInfo(
                originalEstimateMinutes = this?.originalEstimateMinutes ?: 0,
                remainingEstimateMinutes = this?.remainingEstimateMinutes ?: 0,
                timeSpentMinutes = this?.timeSpentMinutes ?: 0
        )
