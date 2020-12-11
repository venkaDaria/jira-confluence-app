package com.globallogic.jiraapp.controllers.scheduled

import com.globallogic.jiraapp.services.scheduled.PersonWorklogMetricScheduledService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/person-worklog")
@ConditionalOnProperty(value = ["metric.metrics.person_worklog.enabled"], havingValue = "true")
class PersonWorklogMetricScheduledController(
        private val personWorklogMetricScheduledService: PersonWorklogMetricScheduledService
) {

    @PostMapping
    fun collectNow() = personWorklogMetricScheduledService.scheduleFixedDelayTask()
}
