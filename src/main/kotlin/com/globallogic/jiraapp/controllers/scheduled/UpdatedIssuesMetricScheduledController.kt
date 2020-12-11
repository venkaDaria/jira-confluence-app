package com.globallogic.jiraapp.controllers.scheduled

import com.globallogic.jiraapp.services.scheduled.UpdatedIssuesMetricScheduledService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/updated-issues")
@ConditionalOnProperty(value = ["metric.metrics.updated_issues.enabled"], havingValue = "true")
class UpdatedIssuesMetricScheduledController(
        private val updatedIssuesMetricScheduledService: UpdatedIssuesMetricScheduledService
) {

    @PostMapping
    fun collectNow() = updatedIssuesMetricScheduledService.scheduleFixedDelayTask()
}
