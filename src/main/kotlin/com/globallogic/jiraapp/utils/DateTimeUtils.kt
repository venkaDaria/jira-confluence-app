package com.globallogic.jiraapp.utils

import com.globallogic.jiraapp.domain.dto.AbstractCommentInfo
import org.joda.time.DateTimeConstants.SATURDAY
import org.joda.time.DateTimeConstants.SUNDAY
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat.forPattern

private val dateFormatter = forPattern("MM/dd/yyyy")

private val dateFormatterChart = forPattern("yyyy-MM-dd")

fun String.parse(): LocalDate =
        LocalDate.parse(this.substringBefore("T"), dateFormatterChart)

fun Long.dateToString(): String = dateFormatter.print(this)

fun LocalDate.dateToString(): String = dateFormatter.print(this)

fun LocalDate.dateToStringForChart(): String = dateFormatterChart.print(this)

fun LocalDate.getMillis(): Long = toDateTimeAtStartOfDay().millis

fun AbstractCommentInfo.isToday(): Boolean = isAfter(LocalDate.now())

fun AbstractCommentInfo.isYesterday(): Boolean = isAfter(LocalDate.now().minusDays(1))

fun AbstractCommentInfo.isEqual(localDate: LocalDate): Boolean = LocalDate(dateTime) == localDate

fun LocalDate.isWorkDay(): Boolean = dayOfWeek !in (SATURDAY..SUNDAY)

private fun AbstractCommentInfo.isAfter(localDate: LocalDate): Boolean = dateTime > localDate.getMillis()
