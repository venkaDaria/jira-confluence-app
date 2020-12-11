package com.globallogic.jiraapp.services.metrics.impl

import com.globallogic.jiraapp.domain.metrics.IssuesPerPersonMetric
import com.globallogic.jiraapp.repositories.metrics.IssuesPerPersonMetricRepository
import com.globallogic.jiraapp.services.metrics.MetricService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class IssuesPerPersonMetricServiceImpl(
        private val issuesPerPersonMetricRepository: IssuesPerPersonMetricRepository
) : MetricService<IssuesPerPersonMetric>, Logging by LoggingImpl<IssuesPerPersonMetricServiceImpl>() {

    override fun findAll(): List<IssuesPerPersonMetric> {
        log.debug("Request to get all IssuesPerPersonMetrics")

        return issuesPerPersonMetricRepository.findAll()
    }

    override fun findOne(id: String): IssuesPerPersonMetric? {
        log.debug("Request to get IssuesPerPersonMetric : {}", id)

        return issuesPerPersonMetricRepository.findByIdOrNull(id)
    }
}
