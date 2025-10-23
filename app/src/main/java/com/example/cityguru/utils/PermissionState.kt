package com.example.cityguru.utils

sealed class PermissionState {
    object Checking : PermissionState()
    object Granted : PermissionState()
    object Denied : PermissionState()
}