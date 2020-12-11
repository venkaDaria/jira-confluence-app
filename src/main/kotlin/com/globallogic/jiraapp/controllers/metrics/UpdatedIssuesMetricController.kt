package com.globallogic.jiraapp.controllers.metrics

import com.globallogic.jiraapp.domain.metrics.UpdatedIssuesMetric
import com.globallogic.jiraapp.services.metrics.impl.UpdatedIssuesMetricServiceImpl
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/updated-issues")
class UpdatedIssuesMetricController(
        private val updatedIssuesMetricService: UpdatedIssuesMetricServiceImpl
) : Logging by LoggingImpl<UpdatedIssuesMetricController>() {

    @GetMapping
    fun allUpdatedIssuesMetrics(): List<UpdatedIssuesMetric> {
        log.debug("REST request to get a page of UpdatedIssuesMetrics")

        return updatedIssuesMetricService.findAll()
    }

    @GetMapping("/{id}")
    fun getUpdatedIssuesMetric(@PathVariable id: String): ResponseEntity<UpdatedIssuesMetric> {
        log.debug("REST request to get UpdatedIssuesMetric : {}", id)

        val updatedIssuesMetric = updatedIssuesMetricService.findOne(id)

        return updatedIssuesMetric?.let { ResponseEntity.ok().body(it) }
                ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
