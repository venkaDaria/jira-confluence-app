package com.globallogic.jiraapp.services.metrics.impl

import com.globallogic.jiraapp.domain.metrics.NotUpdatedIssuesMetric
import com.globallogic.jiraapp.repositories.metrics.NotUpdatedIssuesMetricRepository
import com.globallogic.jiraapp.services.metrics.MetricService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Service Implementation for managing NotUpdatedIssuesMetric.
 */
@Service
class NotUpdatedIssuesMetricServiceImpl(
        private val notUpdatedIssuesMetricRepository: NotUpdatedIssuesMetricRepository
) : MetricService<NotUpdatedIssuesMetric>, Logging by LoggingImpl<NotUpdatedIssuesMetricServiceImpl>() {

    /**
     * Get all the notUpdatedIssuesMetrics.
     *
     * @return the list of entities
     */
    override fun findAll(): List<NotUpdatedIssuesMetric> {
        log.debug("Request to get all NotUpdatedIssuesMetrics")

        return notUpdatedIssuesMetricRepository.findAll()
    }

    /**
     * Get one notUpdatedIssuesMetric by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    override fun findOne(id: String): NotUpdatedIssuesMetric? {
        log.debug("Request to get NotUpdatedIssuesMetric : {}", id)

        return notUpdatedIssuesMetricRepository.findByIdOrNull(id)
    }
}
