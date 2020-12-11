package com.globallogic.jiraapp.services.metrics.impl

import com.globallogic.jiraapp.domain.metrics.UpdatedIssuesMetric
import com.globallogic.jiraapp.repositories.metrics.UpdatedIssuesMetricRepository
import com.globallogic.jiraapp.services.metrics.MetricService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Service Implementation for managing UpdatedIssuesMetric.
 */
@Service
class UpdatedIssuesMetricServiceImpl(
        private val updatedIssuesMetricRepository: UpdatedIssuesMetricRepository
) : MetricService<UpdatedIssuesMetric>, Logging by LoggingImpl<UpdatedIssuesMetricServiceImpl>() {

    /**
     * Get all the UpdatedIssuesMetrics.
     *
     * @return the list of entities
     */
    override fun findAll(): List<UpdatedIssuesMetric> {
        log.debug("Request to get all UpdatedIssuesMetrics")

        return updatedIssuesMetricRepository.findAll()
    }

    /**
     * Get one UpdatedIssuesMetric by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    override fun findOne(id: String): UpdatedIssuesMetric? {
        log.debug("Request to get UpdatedIssuesMetric : {}", id)

        return updatedIssuesMetricRepository.findByIdOrNull(id)
    }
}
