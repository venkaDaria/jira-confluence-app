package com.globallogic.jiraapp.domain.dto

import com.globallogic.jiraapp.domain.enums.MetricStatus

data class UserInfo(
        val login: String?,
        val displayName: String,
        val issues: List<IssueInfo>,
        var status: MetricStatus,

        /* actual yesterday */
        val hoursSpent: Int?
) {

    override fun equals(other: Any?): Boolean =
            if (other is UserInfo) displayName == other.displayName else false

    override fun hashCode(): Int = displayName.hashCode()

    fun withStatus(getStatus: (Int) -> MetricStatus): UserInfo {
        status = getStatus(hoursSpent ?: 0)
        return this
    }
}
