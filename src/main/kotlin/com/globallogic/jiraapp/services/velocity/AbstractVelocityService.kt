package com.globallogic.jiraapp.services.velocity

import com.globallogic.jiraapp.domain.enums.SprintIssues
import org.apache.velocity.Template
import org.apache.velocity.VelocityContext

import java.io.StringWriter

abstract class AbstractVelocityService(
        private val template: Template
) {

    fun <T> renderTemplate(report: List<T>, name: String, sprint: SprintIssues): String {
        val context = VelocityContext()

        context.put("report", report)
        context.put("metricName", name)
        context.put("sprint", sprint)

        val writer = StringWriter()
        template.merge(context, writer)

        return writer.toString()
    }
}
