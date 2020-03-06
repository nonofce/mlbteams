package com.nonofce.android.mlbteams.data

import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.nonofce.android.data.source.LocationDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServiceLocationDataSource(application: Application) : LocationDataSource {

    private val geocoder = Geocoder(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    override suspend fun getZipCode(): String? =
        suspendCancellableCoroutine<String?> { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result.toPostalCode())
            }
        }

    private fun Location?.toPostalCode(): String? {
        val address = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return address?.firstOrNull()?.postalCode
    }
}