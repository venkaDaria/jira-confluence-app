package com.globallogic.jiraapp.services.diagrams

import com.globallogic.jiraapp.domain.diagrams.Diagram
import com.globallogic.jiraapp.domain.enums.DiagramType

interface DiagramService {

    fun findByType(diagramType: DiagramType): Diagram?
}
