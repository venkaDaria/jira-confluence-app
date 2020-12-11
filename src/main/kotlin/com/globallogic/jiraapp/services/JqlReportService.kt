package com.globallogic.jiraapp.services

import com.globallogic.jiraapp.domain.dto.ReportedWorklog

interface JqlReportService {

    fun getReportedWorklog(personCount: Int): ReportedWorklog
}
