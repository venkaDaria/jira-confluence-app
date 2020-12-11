package com.globallogic.jiraapp.services.diagrams.impl

import com.globallogic.jiraapp.domain.diagrams.Diagram
import com.globallogic.jiraapp.services.diagrams.SimpleDiagramService
import com.globallogic.jiraapp.services.metrics.impl.PersonWorklogMetricServiceImpl
import org.springframework.stereotype.Service

@Service
class BurndownChartService(
        private val personWorklogMetricService: PersonWorklogMetricServiceImpl
) : SimpleDiagramService {

    override fun findData(): Diagram? {
        return personWorklogMetricService.findLast()
                ?.let {
                    Diagram(
                            sprintName = it.result.sprint?.name ?: "",
                            remaining = it.result.remainingPerDayHours,
                            stories = it.result.expectedPerDayHours
                    )
                }
    }
}
