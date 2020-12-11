package com.globallogic.jiraapp.services.impl

import com.globallogic.jiraapp.config.dto.ConfluenceProperties
import com.globallogic.jiraapp.domain.enums.ConfluencePageField
import com.globallogic.jiraapp.domain.reports.*
import com.globallogic.jiraapp.services.ConfluenceService
import com.globallogic.jiraapp.utils.logging.Logging
import com.globallogic.jiraapp.utils.logging.LoggingImpl
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import java.lang.String.format

const val CONFLUENCE_URL = "%s/rest/api/content/"

@Service
class ConfluenceServiceImpl(
        private val confluenceProperties: ConfluenceProperties,
        private val confRestTemplate: RestTemplate
) : ConfluenceService, Logging by LoggingImpl<ConfluenceServiceImpl>() {

    override fun createPage(pageTitle: String, content: String) {
        val page = definePage(confluenceProperties.space, confluenceProperties.parent, pageTitle, content)
        val pageRequest = HttpEntity(page)

        confRestTemplate.postForEntity<Any>(format(CONFLUENCE_URL, confluenceProperties.url), pageRequest)
    }
}

fun definePage(space: String, parentId: Long, pageTitle: String, content: String): Page =
        Page(type = ConfluencePageField.TYPE.defaultValue,
                space = defineSpace(space),
                ancestors = defineParentPage(parentId),
                title = pageTitle,
                body = defineBody(content))

private fun defineBody(content: String): Body =
        Body(storage = defineStorage(content))

private fun defineStorage(content: String): Storage =
        Storage(representation = ConfluencePageField.REPRESENTATION.defaultValue,
                value = content)

private fun defineSpace(space: String): Space =
        Space(key = space)

private fun defineParentPage(parentId: Long): List<Ancestor> =
        listOf(Ancestor(id = parentId))
