package com.nonofce.android.mlbteams.model.roster

data class QueryResults(
    val created: String,
    val row: List<Row>,
    val totalSize: String
)