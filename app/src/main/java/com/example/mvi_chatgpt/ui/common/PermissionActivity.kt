package com.example.mvi_chatgpt.ui.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


@Composable
fun PermissionActivity(
    permission: String,
    onResult: (Boolean) -> Unit = {}
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = onResult
    )

    LaunchedEffect(key1 = launcher) {
        launcher.launch(permission)
    }
}

@Composable
fun PermissionsActivity(
    permissions: Array<String>,
    onResult: (Map<String, Boolean>) -> Unit = {}
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = onResult
    )

    LaunchedEffect(key1 = launcher) {
        launcher.launch(permissions)
    }
}