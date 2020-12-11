package com.globallogic.jiraapp.controllers.scheduled

import com.globallogic.jiraapp.services.scheduled.NotUpdatedIssuesMetricScheduledService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/not-updated-issues")
@ConditionalOnProperty(value = ["metric.metrics.not_updated_issues.enabled"], havingValue = "true")
class NotUpdatedIssuesMetricScheduledController(
        private val notUpdatedIssuesMetricScheduledService: NotUpdatedIssuesMetricScheduledService
) {

    @PostMapping
    fun collectNow() = notUpdatedIssuesMetricScheduledService.scheduleFixedDelayTask()
}
