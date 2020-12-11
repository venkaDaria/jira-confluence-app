package com.globallogic.jiraapp.controllers.scheduled

import com.globallogic.jiraapp.services.JqlMetricService
import com.globallogic.jiraapp.services.scheduled.CustomMetricScheduledService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/custom-metrics")
class CustomMetricScheduledController(
        private val customMetricScheduledService: CustomMetricScheduledService,
        private val jqlMetricService: JqlMetricService
) {

    @PostMapping
    fun collectNow() {
        val metrics = jqlMetricService.findCustomMetrics()

        metrics.forEach {
            customMetricScheduledService.simpleTask(it.name, it.jql)
        }
    }
}
