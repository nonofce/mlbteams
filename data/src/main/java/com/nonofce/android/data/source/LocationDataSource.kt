package com.nonofce.android.data.source

interface LocationDataSource {

    suspend fun getZipCode(): String?
}