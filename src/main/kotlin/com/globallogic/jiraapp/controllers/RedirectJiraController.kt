package com.globallogic.jiraapp.controllers

import com.globallogic.jiraapp.config.dto.JiraProperties
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@RequestMapping("/api/jira")
@Controller
class RedirectJiraController(private val jiraProperties: JiraProperties) {

    @GetMapping("/{issueName}")
    fun redirectToIssue(@PathVariable issueName: String): ModelAndView {
        return ModelAndView("redirect:" + jiraProperties.url + "/browse/" + issueName)
    }
}
