package com.example.mvi_chatgpt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mvi_chatgpt.ui.navigation.controller.LocalMainNavController
import com.example.mvi_chatgpt.ui.navigation.controller.MainNavController
import com.example.mvi_chatgpt.ui.navigation.controller.rememberMainNavController
import com.example.mvi_chatgpt.ui.navigation.router.ChatRouter

@Composable
fun AppNavigation(
    navigator: MainNavController = rememberMainNavController(startDestination = Navigation.Routes.CHAT)
) {
    CompositionLocalProvider(
        LocalMainNavController provides navigator
    ) {
        NavHost(
            startDestination = navigator.startDestination,
            navController = navigator.navController
        ) {
            composable(route = Navigation.Routes.CHAT) {
                ChatRouter()
            }
        }
    }
}