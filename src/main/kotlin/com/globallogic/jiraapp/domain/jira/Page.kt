package com.globallogic.jiraapp.domain.jira

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Page(
        val maxResults: Int,
        val startAt: Int,
        val isLast: Boolean,
        val values: Array<Sprint>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Page

        if (maxResults != other.maxResults) return false
        if (startAt != other.startAt) return false
        if (isLast != other.isLast) return false
        if (!values.contentEquals(other.values)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = maxResults
        result = 31 * result + startAt
        result = 31 * result + isLast.hashCode()
        result = 31 * result + values.contentHashCode()
        return result
    }
}
