package com.nonofce.android.data.source

interface PermissionChecker {

    enum class Permission { FINE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}