package com.globallogic.jiraapp.services.impl

import com.globallogic.jiraapp.domain.dto.JqlUpdateMetricDto
import com.globallogic.jiraapp.services.YamlService
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.yaml.snakeyaml.Yaml
import java.io.PrintWriter
import java.util.*

@Service
class YamlServiceImpl(
        private val yaml: Yaml,
        env: Environment
) : YamlService {

    private val fileName = "application-${env.activeProfiles[0]}.yml"

    override fun save(jqlMetricDto: JqlUpdateMetricDto) {
        val inputStream = this.javaClass
                .classLoader
                .getResourceAsStream(fileName)

        val properties = yaml.load<Map<String, Any>>(inputStream).toMutableMap()

        val metrics =
                ((properties["metric"] as LinkedHashMap<String, Any>)["metrics"] as LinkedHashMap<String, Any>)

        val metric = metrics[jqlMetricDto.name.toLowerCase()] as LinkedHashMap<String, Any>

        metric["enabled"] = jqlMetricDto.enabled
        metric["rate"] = jqlMetricDto.rate

        PrintWriter(ResourceUtils.getFile(fileName))
                .use { yaml.dump(properties, it) }
    }
}