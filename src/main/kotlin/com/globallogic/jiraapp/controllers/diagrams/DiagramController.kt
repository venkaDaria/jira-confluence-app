package com.globallogic.jiraapp.controllers.diagrams

import com.globallogic.jiraapp.domain.diagrams.Diagram
import com.globallogic.jiraapp.domain.enums.toDiagramType
import com.globallogic.jiraapp.services.diagrams.DiagramService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/diagram")
class DiagramController(
        private val diagramService: DiagramService
) : Logging by LoggingImpl<DiagramController>() {

    @GetMapping("/{name}")
    fun getDiagramData(@PathVariable name: String): Diagram? {
        log.debug("REST request to get a diagram data by name: $name")

        return diagramService.findByType(name.toDiagramType())
    }
}
