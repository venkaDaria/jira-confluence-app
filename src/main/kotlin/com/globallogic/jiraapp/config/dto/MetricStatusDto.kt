package com.globallogic.jiraapp.config.dto

abstract class MetricStatusDto<T> {

    abstract val yellowThreshold: T
    abstract val redThreshold: T
}

class IntMetricStatusDto : MetricStatusDto<Int>() {

    override var yellowThreshold: Int = 0
    override var redThreshold: Int = 0
}

class DoubleMetricStatusDto : MetricStatusDto<Double>() {

    override var yellowThreshold: Double = 0.0
    override var redThreshold: Double = 0.0
}
