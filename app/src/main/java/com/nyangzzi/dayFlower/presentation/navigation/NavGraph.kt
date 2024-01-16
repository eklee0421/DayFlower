package com.nyangzzi.dayFlower.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nyangzzi.dayFlower.presentation.feature.calendar.CalendarScreen
import com.nyangzzi.dayFlower.presentation.feature.home.HomeScreen
import com.nyangzzi.dayFlower.presentation.feature.locker.LockerScreen
import com.nyangzzi.dayFlower.presentation.feature.login.LoginScreen
import com.nyangzzi.dayFlower.presentation.feature.mainFlower.MainFlowerScreen
import com.nyangzzi.dayFlower.presentation.feature.profile.ProfileScreen
import com.nyangzzi.dayFlower.presentation.feature.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController) {

    navController.stackLog("main")

    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {

        composable(route = Screens.Login.route) {
            LoginScreen(onNavigate = { navController.navigateSingleTopTo(it.route) })
        }
        composable(route = Screens.MainFlower.route) {
            MainFlowerScreen(onNavigate = { navController.navigateSingleTopTo(it.route) })
        }
    }
}

@Composable
fun HomeNavGraph(navController: NavHostController) {

    navController.stackLog("home bottom")

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {

        composable(route = Screens.Home.route) {
            HomeScreen()
        }
        composable(route = Screens.Calendar.route) {
            CalendarScreen()
        }
        composable(route = Screens.Search.route) {
            SearchScreen()
        }
        composable(route = Screens.Locker.route) {
            LockerScreen()
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen()
        }

    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.popBackStack()
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            //모든화면을 닫은 후 새 화면 열기
            inclusive = true
            //하단 탐색 항목 간 전환시 상태와 백 스택이 올바르게 저장
            saveState = true
        }
        //동일한 항목을 선택할때 여러번 복사를 방지
        launchSingleTop = true
        //하단 탐색 항목 간 전환시 상태와 백 스택이 복원
        //restoreState = true
    }
}

fun NavHostController.navigateBottom(route: String) {
    this.popBackStack()
    this.navigate(route) {
        popUpTo(
            this@navigateBottom.graph.findStartDestination().id,
        ) {
            saveState = true
        }

        /*this@navigateBottom.graph.startDestinationRoute?.let {
            popUpTo(it) { saveState = true }
        }
        //동일한 항목을 선택할때 여러번 복사를 방지
        launchSingleTop = true
        //하단 탐색 항목 간 전환시 상태와 백 스택이 복원
        restoreState = true
    }
}

fun NavHostController.stackLog(nav: String) {
    this.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller.backQueue.map { it.destination.route }.joinToString(", ")

        Log.d("$nav BackStackLog", "BackStack: $routes")
    }
}


fun NavHostController.onNavigateNext(
    nowScreen: Screens,
    argument: String? = null,
) {

    val destination =
        if (argument != null) nowScreen.routeByArgs(argument) else nowScreen.route

    this.navigateSingleTopTo(destination)

}