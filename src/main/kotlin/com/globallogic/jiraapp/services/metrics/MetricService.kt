package com.globallogic.jiraapp.services.metrics

/**
 * Service Interface for managing NotUpdatedIssuesMetric.
 */
interface MetricService<T> {

    /**
     * Get all metrics.
     *
     * @return the list of entities
     */
    fun findAll(): List<T> {
        TODO("not implemented")
    }

    /**
     * Get the "id" metric.
     *
     * @param id the id of the entity
     * @return the entity
     */
    fun findOne(id: String): T?

    fun findAllByName(name: String): List<T> {
        TODO("not implemented")
    }
}
