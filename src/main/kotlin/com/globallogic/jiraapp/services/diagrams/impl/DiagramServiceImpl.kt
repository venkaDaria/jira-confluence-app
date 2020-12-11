package com.globallogic.jiraapp.services.diagrams.impl

import com.globallogic.jiraapp.domain.diagrams.Diagram
import com.globallogic.jiraapp.domain.enums.DiagramType
import com.globallogic.jiraapp.services.diagrams.DiagramService
import org.springframework.stereotype.Service

@Service
class DiagramServiceImpl(
        private val burndownChartService: BurndownChartService
) : DiagramService {

    override fun findByType(diagramType: DiagramType): Diagram? = when (diagramType) {
        DiagramType.BURNDOWN -> burndownChartService.findData()
        DiagramType.NOTHING -> null
    }
}
