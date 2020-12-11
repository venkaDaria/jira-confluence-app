package com.globallogic.jiraapp.domain.enums

enum class DiagramType(val type: String) {

    BURNDOWN("burndown"),
    NOTHING("nothing")
}

fun String.toDiagramType(): DiagramType = DiagramType.values().find { it.type == this } ?: DiagramType.NOTHING
