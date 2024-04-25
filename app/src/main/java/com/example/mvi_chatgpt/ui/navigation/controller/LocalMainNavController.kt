package com.example.mvi_chatgpt.ui.navigation.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalMainNavController: ProvidableCompositionLocal<MainNavController> =
    compositionLocalOf { error("No MainNavController provided") }

val currentMainNavController: MainNavController
    @Composable
    @ReadOnlyComposable
    get() = LocalMainNavController.current