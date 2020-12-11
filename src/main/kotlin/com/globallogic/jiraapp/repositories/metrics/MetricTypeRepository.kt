package com.globallogic.jiraapp.repositories.metrics

import com.globallogic.jiraapp.domain.metrics.MetricType
import org.springframework.data.mongodb.repository.MongoRepository

interface MetricTypeRepository : MongoRepository<MetricType, String> {

    fun findAllByDeletedIsFalse(): List<MetricType>

    fun findByName(name: String): MetricType
}
