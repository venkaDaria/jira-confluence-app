package com.globallogic.jiraapp.services.velocity.impl

import com.globallogic.jiraapp.services.velocity.AbstractVelocityService
import org.apache.velocity.Template
import org.springframework.stereotype.Service

@Service
class PersonVelocityServiceImpl(personReport: Template) : AbstractVelocityService(personReport)
