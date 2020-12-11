package com.globallogic.jiraapp.controllers

import com.globallogic.jiraapp.domain.dto.JqlCreateMetricDto
import com.globallogic.jiraapp.domain.dto.JqlMetricDto
import com.globallogic.jiraapp.domain.dto.JqlUpdateMetricDto
import com.globallogic.jiraapp.services.JqlMetricService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.cloud.context.scope.refresh.RefreshScope
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/metrics")
class MetricController(
        private val refreshScope: RefreshScope,
        private val environment: ConfigurableEnvironment,
        private val jqlMetricService: JqlMetricService
) : Logging by LoggingImpl<MetricController>() {
    private val metrics = "metric.metrics"

    @GetMapping
    fun allMetrics(): List<JqlMetricDto> {
        log.debug("GET request to get a page of Metrics")

        return jqlMetricService.findAll()
    }

    private val redirect = "http://localhost:3000/#/metrics"

    @PostMapping
    fun addMetric(jqlMetricDto: JqlCreateMetricDto, response: HttpServletResponse) {
        log.debug("POST request to add a new metric: $jqlMetricDto")

        jqlMetricService.save(jqlMetricDto)

        response.sendRedirect(redirect)
    }

    @PutMapping
    fun updateMetric(@RequestBody jqlMetricDto: JqlUpdateMetricDto, response: HttpServletResponse) {
        log.debug("PUT request to update a metric: $jqlMetricDto")

        if (jqlMetricDto.custom) {
            jqlMetricService.save(jqlMetricDto)
        } else {
            environment.systemProperties["$metrics.${jqlMetricDto.name}.enabled"] = jqlMetricDto.enabled
            environment.systemProperties["$metrics.${jqlMetricDto.name}.rate"] = jqlMetricDto.rate
        }

        refreshScope.refreshAll()

        response.sendRedirect(redirect)
    }

    @DeleteMapping("/{metric}")
    fun deleteMetric(@PathVariable metric: String, response: HttpServletResponse) {
        log.debug("DELETE request to delete a metric $metric")

        jqlMetricService.deleteByName(metric)

        response.sendRedirect(redirect)
    }
}
