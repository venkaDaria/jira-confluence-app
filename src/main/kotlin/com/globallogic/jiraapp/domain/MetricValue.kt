package com.globallogic.jiraapp.domain

import com.globallogic.jiraapp.domain.enums.MetricStatus
import org.springframework.data.annotation.Transient

abstract class MetricValue<T, R>(
        @Transient
        open val calculationTime: Long,
        @Transient
        open val metric: R,
        @Transient
        open val result: T,
        @Transient
        open val status: MetricStatus,
        @Transient
        open var id: String?
)
