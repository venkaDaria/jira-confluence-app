package com.globallogic.jiraapp.domain.diagrams

data class Diagram(
        val sprintName: String,
        val stories: Map<String, Double>,
        val remaining: Map<String, Double>
)
