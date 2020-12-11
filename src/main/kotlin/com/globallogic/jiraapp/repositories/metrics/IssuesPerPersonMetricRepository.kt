package com.globallogic.jiraapp.repositories.metrics

import com.globallogic.jiraapp.domain.metrics.IssuesPerPersonMetric
import org.springframework.data.mongodb.repository.MongoRepository

interface IssuesPerPersonMetricRepository : MongoRepository<IssuesPerPersonMetric, String>
