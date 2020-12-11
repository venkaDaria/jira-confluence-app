package com.globallogic.jiraapp.domain.reports

import com.globallogic.jiraapp.domain.dto.UserInfo

data class PersonReportItem(
        val login: String,
        val displayName: String,
        // map: status to list of issues' keys
        val issues: Map<String, List<IssueReportItem>>
)

fun getPersonReportItem(userInfo: UserInfo): PersonReportItem =
        PersonReportItem(
                login = userInfo.login ?: "",
                displayName = userInfo.displayName,
                issues = userInfo.issues.groupBy {
                    it.status
                }.mapValues { issues ->
                    issues.value.map {
                        it.getIssueReportItemWithHistory()
                    }
                }
        )
