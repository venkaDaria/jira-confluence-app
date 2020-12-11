package com.globallogic.jiraapp.controllers.metrics

import com.globallogic.jiraapp.domain.metrics.CustomIssuesMetric
import com.globallogic.jiraapp.services.metrics.impl.CustomMetricServiceImpl
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/custom-metric")
class CustomMetricController(
        private val customMetricService: CustomMetricServiceImpl
) : Logging by LoggingImpl<CustomMetricController>() {

    @GetMapping("/{name}")
    fun allCustomMetrics(@PathVariable name: String): List<CustomIssuesMetric> {
        log.debug("REST request to get a page of CustomMetrics")

        return customMetricService.findAllByName(name)
    }

    @GetMapping("/{id}")
    fun getCustomMetric(@PathVariable id: String): ResponseEntity<CustomIssuesMetric> {
        log.debug("REST request to get CustomMetric : {}", id)

        val notUpdatedIssuesMetric = customMetricService.findOne(id)

        return notUpdatedIssuesMetric?.let { ResponseEntity.ok().body(it) }
                ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
