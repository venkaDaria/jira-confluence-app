package com.globallogic.jiraapp.controllers.metrics

import com.globallogic.jiraapp.domain.metrics.IssuesPerPersonMetric
import com.globallogic.jiraapp.services.metrics.impl.IssuesPerPersonMetricServiceImpl
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/issues-per-person")
class IssuesPerPersonMetricController(
        private val issuesPerPersonMetricService: IssuesPerPersonMetricServiceImpl
) : Logging by LoggingImpl<IssuesPerPersonMetricController>() {

    @GetMapping
    fun allIssuesPerPersonMetrics(): List<IssuesPerPersonMetric> {
        log.debug("REST request to get a page of IssuesPerPersonMetrics")

        return issuesPerPersonMetricService.findAll()
    }

    @GetMapping("/{id}")
    fun getIssuesPerPersonMetric(@PathVariable id: String): ResponseEntity<IssuesPerPersonMetric> {
        log.debug("REST request to get IssuesPerPersonMetric : {}", id)

        val issuesPerPersonMetric = issuesPerPersonMetricService.findOne(id)

        return issuesPerPersonMetric?.let { ResponseEntity.ok().body(it) }
                ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
