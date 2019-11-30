package com.nonofce.android.mlbteams.model.results.teams

data class QueryResults(
    val created: String,
    val row: List<Row>,
    val totalSize: String
)