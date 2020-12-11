package com.globallogic.jiraapp.services.metrics.impl

import com.globallogic.jiraapp.domain.metrics.PersonWorklogMetric
import com.globallogic.jiraapp.repositories.metrics.PersonWorklogMetricRepository
import com.globallogic.jiraapp.services.metrics.MetricService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PersonWorklogMetricServiceImpl(
        private val personWorklogMetricRepository: PersonWorklogMetricRepository
) : MetricService<PersonWorklogMetric>, Logging by LoggingImpl<PersonWorklogMetricServiceImpl>() {

    override fun findAll(): List<PersonWorklogMetric> {
        log.debug("Request to get all PersonWorklogMetrics")

        return personWorklogMetricRepository.findAll()
    }

    override fun findOne(id: String): PersonWorklogMetric? {
        log.debug("Request to get PersonWorklogMetric : {}", id)

        return personWorklogMetricRepository.findByIdOrNull(id)
    }

    fun findLast(): PersonWorklogMetric? {
        log.debug("Request to get last PersonWorklogMetric")

        return personWorklogMetricRepository.findTopByOrderByCalculationTimeDescOrNull()
    }
}

fun PersonWorklogMetricRepository.findTopByOrderByCalculationTimeDescOrNull(): PersonWorklogMetric? =
        findTopByOrderByCalculationTimeDesc().orElse(null)
