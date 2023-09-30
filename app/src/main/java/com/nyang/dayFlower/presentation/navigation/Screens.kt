package com.nyang.dayFlower.presentation.navigation

sealed class Screens(val route: String, val argumentKey: String = "") {
    object FlowerCalendar : Screens(route = "flowerCalendar")
    object FlowerDetail: Screens(route = "flowerDetail")

    fun routeByArgs(query: String?): String =
        if (argumentKey.isNotBlank() && query != null) "$route?$argumentKey=$query" else route
}