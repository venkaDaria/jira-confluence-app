package com.globallogic.jiraapp.services

import com.globallogic.jiraapp.domain.dto.JqlUpdateMetricDto

interface YamlService {

    fun save(jqlMetricDto: JqlUpdateMetricDto)
}
