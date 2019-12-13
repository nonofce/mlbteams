package com.nonofce.android.mlbteams.model.teams

import com.nonofce.android.mlbteams.model.teams.Row

data class QueryResults(
    val created: String,
    val row: List<Row>,
    val totalSize: String
)