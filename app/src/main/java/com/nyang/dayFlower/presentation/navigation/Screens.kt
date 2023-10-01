package com.nyang.dayFlower.presentation.navigation

sealed class Screens(val route: String, val argumentKey: String = "") {
    object MainFlower: Screens(route = "mainFlower")

    fun routeByArgs(query: String?): String =
        if (argumentKey.isNotBlank() && query != null) "$route?$argumentKey=$query" else route
}