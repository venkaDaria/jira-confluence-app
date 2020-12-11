package com.globallogic.jiraapp.domain.jira

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.globallogic.jiraapp.utils.isWorkDay
import com.globallogic.jiraapp.utils.parse
import org.joda.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Sprint(
        val id: Int,
        val self: String?,
        val name: String,
        val startDate: String,
        val endDate: String,
        val originalBoardId: Int,
        val goal: String?
) {

    val terms: List<LocalDate>
        get() {
            val start = startDate.parse()
            val end = endDate.parse()

            return (start..end)
                    .iterator().asSequence().toList()
                    .filter { it.isWorkDay() }
        }
}

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> {
    return object : Iterator<LocalDate> {
        private var next = this@iterator.start

        private val finalElement = this@iterator.endInclusive

        private var hasNext = !next.isAfter(this@iterator.endInclusive)

        override fun hasNext(): Boolean = hasNext

        override fun next(): LocalDate {
            val value = next
            if (value == finalElement) {
                hasNext = false
            } else {
                next = next.plusDays(1)

            }
            return value
        }
    }
}

