package com.nonofce.android.mlbteams.data.server.model.roster

data class QueryResults(
    val created: String,
    val row: List<Row>?,
    val totalSize: String
)