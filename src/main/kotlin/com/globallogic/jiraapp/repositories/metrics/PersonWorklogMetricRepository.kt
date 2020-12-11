package com.globallogic.jiraapp.repositories.metrics

import com.globallogic.jiraapp.domain.metrics.PersonWorklogMetric
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface PersonWorklogMetricRepository : MongoRepository<PersonWorklogMetric, String> {

    fun findTopByOrderByCalculationTimeDesc(): Optional<PersonWorklogMetric>
}
