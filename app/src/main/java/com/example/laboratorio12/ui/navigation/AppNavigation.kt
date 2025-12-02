package com.example.laboratorio12.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboratorio12.ui.auth.LoginScreen
import com.example.laboratorio12.ui.auth.RegisterScreen
import com.example.laboratorio12.ui.home.HomeScreen

enum class AppScreens(val route: String) {
    LOGIN("login"),
    REGISTER("register"),
    HOME("home")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LOGIN.route
    ) {
        composable(AppScreens.LOGIN.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppScreens.HOME.route) {
                        popUpTo(AppScreens.LOGIN.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppScreens.REGISTER.route)
                }
            )
        }
        composable(AppScreens.REGISTER.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(AppScreens.HOME.route) {
                        popUpTo(AppScreens.REGISTER.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(AppScreens.HOME.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(AppScreens.LOGIN.route) {
                        popUpTo(AppScreens.HOME.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
