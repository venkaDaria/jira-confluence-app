package com.globallogic.jiraapp.services.diagrams

import com.globallogic.jiraapp.domain.diagrams.Diagram

interface SimpleDiagramService {

    fun findData(): Diagram?
}
