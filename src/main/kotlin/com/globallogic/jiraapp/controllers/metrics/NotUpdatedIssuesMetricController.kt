package com.globallogic.jiraapp.controllers.metrics

import com.globallogic.jiraapp.domain.metrics.NotUpdatedIssuesMetric
import com.globallogic.jiraapp.services.metrics.impl.NotUpdatedIssuesMetricServiceImpl
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/not-updated-issues")
class NotUpdatedIssuesMetricController(
        private val notUpdatedIssuesMetricService: NotUpdatedIssuesMetricServiceImpl
) : Logging by LoggingImpl<NotUpdatedIssuesMetricController>() {

    @GetMapping
    fun allNotUpdatedIssuesMetrics(): List<NotUpdatedIssuesMetric> {
        log.debug("REST request to get a page of NotUpdatedIssuesMetrics")

        return notUpdatedIssuesMetricService.findAll()
    }

    @GetMapping("/{id}")
    fun getNotUpdatedIssuesMetric(@PathVariable id: String): ResponseEntity<NotUpdatedIssuesMetric> {
        log.debug("REST request to get NotUpdatedIssuesMetric : {}", id)

        val notUpdatedIssuesMetric = notUpdatedIssuesMetricService.findOne(id)

        return notUpdatedIssuesMetric?.let { ResponseEntity.ok().body(it) }
                ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
