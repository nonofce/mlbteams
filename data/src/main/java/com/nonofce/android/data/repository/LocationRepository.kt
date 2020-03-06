package com.nonofce.android.data.repository

import com.nonofce.android.data.source.LocationDataSource
import com.nonofce.android.data.source.PermissionChecker

class LocationRepository(
    private val permissionChecker: PermissionChecker,
    private val locationDataSource: LocationDataSource
) {

    companion object {
        const val DEFAULT_ZIP_CODE = ""
    }

    suspend fun getZipCode(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.FINE_LOCATION)) {
            locationDataSource.getZipCode() ?: DEFAULT_ZIP_CODE
        } else {
            DEFAULT_ZIP_CODE
        }
    }
}