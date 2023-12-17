package com.full.fichaje.navigation

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.full.crm.ui.theme.agenda.Fichajes
import com.full.crm.ui.theme.agenda.FicharViewModel
import com.full.crm.ui.theme.login.Login
import com.full.crm.ui.theme.login.LoginViewModel
import com.full.fichaje.LockScreenOrientation


object NavigationManager{
    var instance: NavHostController? = null;
    @Composable
    fun InitializeNavigator() {

        if (instance == null) instance = rememberNavController()

        NavHost(navController = instance!!, startDestination = AppScreens.Login.route)
        {
            composable(AppScreens.Login.route) {
                Login(loginViewModel = LoginViewModel())
                LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            composable(AppScreens.Fichaje.route) {
                Fichajes(ficharViewModel = FicharViewModel())
                LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
        }
    }
}