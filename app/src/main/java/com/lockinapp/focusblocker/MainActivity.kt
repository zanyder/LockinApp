package com.lockinapp.focusblocker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lockinapp.focusblocker.ui.screens.BlockedAppsScreen
import com.lockinapp.focusblocker.ui.screens.HomeScreen
import com.lockinapp.focusblocker.ui.screens.OnboardingScreen
import com.lockinapp.focusblocker.ui.screens.PermissionsScreen
import com.lockinapp.focusblocker.ui.screens.SessionSetupScreen
import com.lockinapp.focusblocker.ui.screens.SettingsScreen
import com.lockinapp.focusblocker.ui.theme.LockinFocusTheme
import com.lockinapp.focusblocker.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LockinFocusApp()
        }
    }
}

@Composable
fun LockinFocusApp() {
    LockinFocusTheme {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colorScheme.background) {
            AppNavHost(navController = navController)
        }
    }
}

object Routes {
    const val Onboarding = "onboarding"
    const val Home = "home"
    const val BlockedApps = "blockedApps"
    const val SessionSetup = "sessionSetup"
    const val Settings = "settings"
    const val Permissions = "permissions"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Routes.Onboarding
) {
    val homeViewModel: HomeViewModel = viewModel()
    val uiState by homeViewModel.uiState

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Onboarding) {
            OnboardingScreen(
                onContinue = { navController.navigate(Routes.Permissions) }
            )
        }
        composable(Routes.Home) {
            HomeScreen(
                state = uiState,
                onStartSessionClick = { navController.navigate(Routes.SessionSetup) },
                onManageBlockedAppsClick = { navController.navigate(Routes.BlockedApps) },
                onSettingsClick = { navController.navigate(Routes.Settings) },
                onPermissionsClick = { navController.navigate(Routes.Permissions) }
            )
        }
        composable(Routes.BlockedApps) {
            BlockedAppsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SessionSetup) {
            SessionSetupScreen(
                onBack = { navController.popBackStack() },
                onSessionStarted = {
                    navController.popBackStack(Routes.Home, inclusive = false)
                }
            )
        }
        composable(Routes.Settings) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.Permissions) {
            PermissionsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

