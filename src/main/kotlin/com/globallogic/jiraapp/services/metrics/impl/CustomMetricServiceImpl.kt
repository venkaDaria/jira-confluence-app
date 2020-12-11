package com.globallogic.jiraapp.services.metrics.impl

import com.globallogic.jiraapp.domain.metrics.CustomIssuesMetric
import com.globallogic.jiraapp.repositories.metrics.CustomMetricRepository
import com.globallogic.jiraapp.services.metrics.MetricService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomMetricServiceImpl(
        private val customMetricRepository: CustomMetricRepository
) : MetricService<CustomIssuesMetric>, Logging by LoggingImpl<CustomMetricServiceImpl>() {

    override fun findAllByName(name: String): List<CustomIssuesMetric> {
        log.debug("Request to get all CustomMetrics")

        return customMetricRepository.findAllByMetric(name)
    }

    override fun findOne(id: String): CustomIssuesMetric? {
        log.debug("Request to get CustomMetric : {}", id)

        return customMetricRepository.findByIdOrNull(id)
    }
}
