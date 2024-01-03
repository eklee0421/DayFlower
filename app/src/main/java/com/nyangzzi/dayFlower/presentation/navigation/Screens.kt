package com.nyangzzi.dayFlower.presentation.navigation

sealed class Screens(val route: String, val argumentKey: String = "") {
    object MainFlower : Screens(route = "mainFlower")
    object Login : Screens(route = "login")
    object Home : Screens(route = "home")
    object Calendar : Screens(route = "calendar")
    object Search : Screens(route = "search")
    object Locker : Screens(route = "locker")
    object Profile : Screens(route = "profile")

    fun routeByArgs(query: String?): String =
        if (argumentKey.isNotBlank() && query != null) "$route?$argumentKey=$query" else route
}