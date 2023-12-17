package com.full.fichaje.navigation

sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Fichaje : AppScreens("fichajes")
}
