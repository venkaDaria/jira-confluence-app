package com.globallogic.jiraapp.repositories

import com.globallogic.jiraapp.domain.metrics.AbstractIssuesMetric
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface IssuesMetricRepository<T, R> : MongoRepository<T, String> where T : AbstractIssuesMetric<R>
