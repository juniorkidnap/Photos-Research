package com.app.composetraining.framework.presentation.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.app.composetraining.framework.presentation.theme.ComposeTrainingTheme
import com.app.composetraining.framework.presentation.ui.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity
 *
 * Setting Application Navigation Host and checking premissions
 * for saving photos to internal storage
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var isReadPermissionGranted = false
        var isWritePermissionGranted = false
    }
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoResearchApp()
        }
        checkPermissions()
    }

    private fun checkPermissions() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE]
                ?: isReadPermissionGranted
            isWritePermissionGranted =
                permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: isWritePermissionGranted

        }
        requestPermission()
    }

    private fun requestPermission() {

        val isReadPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val isWritePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdkLevel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        isReadPermissionGranted = isReadPermission
        isWritePermissionGranted = isWritePermission || minSdkLevel

        val permissionRequest = mutableListOf<String>()
        if (!isWritePermissionGranted) {
            permissionRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!isReadPermissionGranted) {
            permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }
}

@Composable
fun PhotoResearchApp() {
    ComposeTrainingTheme {
        Surface {
            AppNavHost()
        }
    }
}
