package com.globallogic.jiraapp.controllers.metrics

import com.globallogic.jiraapp.domain.metrics.PersonWorklogMetric
import com.globallogic.jiraapp.services.metrics.impl.PersonWorklogMetricServiceImpl
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/person-worklog")
class PersonWorklogMetricController(
        private val personWorklogMetricService: PersonWorklogMetricServiceImpl
) : Logging by LoggingImpl<PersonWorklogMetricController>() {

    @GetMapping
    fun allPersonWorklogMetrics(): List<PersonWorklogMetric> {
        log.debug("REST request to get a page of PersonWorklogMetrics")

        return personWorklogMetricService.findAll()
    }

    @GetMapping("/{id}")
    fun getPersonWorklogMetric(@PathVariable id: String): ResponseEntity<PersonWorklogMetric> {
        log.debug("REST request to get PersonWorklogMetric : {}", id)

        val personWorklogMetric = personWorklogMetricService.findOne(id)

        return personWorklogMetric?.let { ResponseEntity.ok().body(it) }
                ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
