package com.example.mvi_chatgpt.ui.navigation.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainNavController(
    val navController: NavHostController,
    val startDestination: String
) {
    private val currentDestination: NavDestination?
    @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun popBackStack() {
        navController.popBackStack()
    }

    fun clearBackStack() {
        val options = NavOptions.Builder()
            .setPopUpTo(navController.graph.findStartDestination().id, inclusive = false)
            .build()
        navController.navigate(startDestination, options)
    }

    fun isSameCurrentDestination(route: String): Boolean {
        return navController.currentDestination?.route == route
    }
}

@Composable
fun rememberMainNavController(
    startDestination: String,
    navController: NavHostController = rememberNavController()
): MainNavController {
    return remember(navController) { MainNavController(navController, startDestination) }
}