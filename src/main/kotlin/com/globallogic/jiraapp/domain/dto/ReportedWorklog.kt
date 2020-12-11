package com.globallogic.jiraapp.domain.dto

import com.globallogic.jiraapp.domain.jira.Sprint

data class ReportedWorklog(
        val expectedSprintHours: Double,
        val actualSprintHours: Double,
        val expectedYesterdayHours: Double,
        val actualYesterdayHours: Double,
        val expectedPerDayHours: Map<String, Double>,
        val remainingPerDayHours: Map<String, Double>,
        val sprint: Sprint?
)
