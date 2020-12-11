package com.globallogic.jiraapp.services.impl

import com.globallogic.jiraapp.config.dto.MetricProperties
import com.globallogic.jiraapp.domain.dto.JqlCreateMetricDto
import com.globallogic.jiraapp.domain.dto.JqlMetricDto
import com.globallogic.jiraapp.domain.dto.JqlUpdateMetricDto
import com.globallogic.jiraapp.domain.enums.JqlMetric
import com.globallogic.jiraapp.domain.metrics.MetricType
import com.globallogic.jiraapp.repositories.metrics.MetricTypeRepository
import com.globallogic.jiraapp.services.JqlMetricService
import com.globallogic.jiraapp.services.scheduled.CustomMetricScheduledService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service
import java.util.concurrent.ScheduledFuture

@Service
class JqlMetricServiceImpl(
        private val threadPoolTaskScheduler: ThreadPoolTaskScheduler,
        private val customMetricScheduledService: CustomMetricScheduledService,
        private val metricProperties: MetricProperties,
        private val metricTypeRepository: MetricTypeRepository
) : JqlMetricService, Logging by LoggingImpl<JqlMetricServiceImpl>() {

    private val schedulerFutures = mutableMapOf<String, ScheduledFuture<*>>()

    override fun findAll(): List<JqlMetricDto> {
        log.debug("Request to get all metrics")

        val defaultMetrics = JqlMetric.values()
                .filter { it.isShown }
                .map {
                    JqlMetricDto(id = it.ordinal.toString(), name = it.name,
                            rate = metricProperties.getMetric(it.name)!!.rate,
                            enabled = metricProperties.getMetric(it.name)!!.isEnabled)
                }

        val customMetrics = metricTypeRepository.findAllByDeletedIsFalse()
                .map {
                    JqlMetricDto(id = it.id, name = it.name, rate = it.rate, enabled = it.enabled, custom = true)
                }

        return defaultMetrics + customMetrics
    }

    override fun findCustomMetrics(): List<MetricType> {
        log.debug("Request to get all custom metrics")

        return metricTypeRepository.findAllByDeletedIsFalse()
    }

    override fun save(jqlMetricDto: JqlCreateMetricDto) {
        val metricType = MetricType(
                name = jqlMetricDto.name,
                rate = jqlMetricDto.rate,
                jql = jqlMetricDto.jql
        )

        registerSchedule(metricType)
        metricTypeRepository.save(metricType)
    }

    override fun save(jqlMetricDto: JqlUpdateMetricDto) {
        val metricType = metricTypeRepository.findByName(jqlMetricDto.name)

        metricType.enabled = jqlMetricDto.enabled
        metricType.rate = jqlMetricDto.rate

        registerSchedule(metricType)
        metricTypeRepository.save(metricType)
    }

    override fun deleteByName(name: String) {
        val metricType = metricTypeRepository.findByName(name)
        metricType.deleted = true
        metricTypeRepository.save(metricType)
    }

    private fun registerSchedule(metricType: MetricType) {
        schedulerFutures[metricType.name]?.cancel(false)

        val scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(
                { customMetricScheduledService.simpleTask(metricType.name, metricType.jql) },
                metricType.rate.toLong()
        )

        schedulerFutures[metricType.name] = scheduledFuture
    }
}
