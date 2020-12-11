package com.globallogic.jiraapp.services

import com.globallogic.jiraapp.domain.dto.JqlCreateMetricDto
import com.globallogic.jiraapp.domain.dto.JqlMetricDto
import com.globallogic.jiraapp.domain.dto.JqlUpdateMetricDto

/**
 * Service Interface for managing Index.
 */
interface JqlMetricService {

    /**
     * Get all the metrics.
     *
     * @return the list of entities
     */
    fun findAll(): List<JqlMetricDto>

    /**
     * Save a new custom metric
     *
     * @param jqlMetricDto JqlCreateMetricDto
     */
    fun save(jqlMetricDto: JqlCreateMetricDto)

    /**
     * Update a metric
     *
     * @param jqlMetricDto JqlUpdateMetricDto
     */
    fun save(jqlMetricDto: JqlUpdateMetricDto)

    /**
     * Delete a metric by name
     *
     * @param name String unique metric name
     */
    fun deleteByName(name: String)

    fun findCustomMetrics(): List<com.globallogic.jiraapp.domain.metrics.MetricType>
}
