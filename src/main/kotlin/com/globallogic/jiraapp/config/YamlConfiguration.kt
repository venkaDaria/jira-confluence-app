package com.globallogic.jiraapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.yaml.snakeyaml.Yaml

@Configuration
class YamlConfiguration {

    @Bean
    fun yaml(): Yaml = Yaml()
}
