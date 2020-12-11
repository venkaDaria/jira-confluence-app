package com.globallogic.jiraapp.config

import org.apache.velocity.Template
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants.RESOURCE_LOADER
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class VelocityConfiguration {

    @Bean
    fun velocityProperties(): Properties {
        val p = Properties()

        p.setProperty(RESOURCE_LOADER, "class")
        p.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")

        return p
    }

    @Bean
    fun velocityEngine(velocityProperties: Properties): VelocityEngine {
        val velocityEngine = VelocityEngine()

        velocityEngine.init(velocityProperties)

        return velocityEngine
    }

    @Bean
    fun simpleReport(velocityEngine: VelocityEngine): Template =
            velocityEngine.getTemplate("/templates/simple-report.vm")

    @Bean
    fun fullReport(velocityEngine: VelocityEngine): Template =
            velocityEngine.getTemplate("/templates/full-report.vm")

    @Bean
    fun personReport(velocityEngine: VelocityEngine): Template =
            velocityEngine.getTemplate("/templates/person-report.vm")
}
