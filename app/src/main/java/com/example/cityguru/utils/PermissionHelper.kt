package com.example.cityguru.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

object PermissionHelper {
    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }
}

@Composable
fun RequestLocationPermission(
    onPermissionGranted: @Composable () -> Unit = {},
    onPermissionDenied: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    var permissionState by remember { mutableStateOf<PermissionState>(PermissionState.Checking) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        permissionState = if (fineLocationGranted || coarseLocationGranted) {
            PermissionState.Granted
        } else {
            PermissionState.Denied
        }
    }

    LaunchedEffect(Unit) {
        if (!PermissionHelper.hasLocationPermission(context)) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            permissionState = PermissionState.Granted
        }
    }

    when (permissionState) {
        PermissionState.Granted -> onPermissionGranted()
        PermissionState.Denied -> onPermissionDenied()
        PermissionState.Checking -> {}
    }
}