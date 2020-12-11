package com.globallogic.jiraapp.repositories

import com.globallogic.jiraapp.domain.reports.IssueReportItem
import org.joda.time.LocalDate
import org.springframework.data.mongodb.repository.MongoRepository

interface ReportItemRepository : MongoRepository<IssueReportItem, String> {

    fun findByCalculationDate(date: LocalDate): List<IssueReportItem>
}
