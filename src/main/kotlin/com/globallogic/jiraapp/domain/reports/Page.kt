package com.globallogic.jiraapp.domain.reports

data class Ancestor(val id: Long)

data class Body(val storage: Storage)

data class Page(val type: String, val title: String, val ancestors: List<Ancestor>, val space: Space, val body: Body)

data class Storage(val value: String, val representation: String)

data class Space(val key: String)
