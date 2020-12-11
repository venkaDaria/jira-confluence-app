package com.globallogic.jiraapp.controllers.scheduled

import com.globallogic.jiraapp.services.scheduled.IssuesPerPersonMetricServiceForScheduling
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/issues-per-person")
class IssuesPerPersonMetricScheduledController(
        private val issuesPerPersonMetricServiceForScheduling: IssuesPerPersonMetricServiceForScheduling
) {

    @PostMapping
    fun collectNow() = issuesPerPersonMetricServiceForScheduling.calculateIssuesPerPerson()
}
