package com.nyang.dayFlower.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nyang.dayFlower.presentation.feature.flowerCalendar.FlowerCalendarScreen
import com.nyang.dayFlower.presentation.feature.flowerDetail.FlowerDetailScreen

@Composable
fun NavGraph(navController: NavHostController, nowScreen: (Screens)->Unit) {

    NavHost(navController = navController,
        startDestination = Screens.FlowerDetail.route){

        composable(route = Screens.FlowerCalendar.route){
            nowScreen(Screens.FlowerCalendar)
            FlowerCalendarScreen()
        }

        composable(route = Screens.FlowerDetail.route){
            nowScreen(Screens.FlowerDetail)
            FlowerDetailScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            //모든화면을 닫은 후 새 화면 열기
            inclusive = true
            //하단 탐색 항목 간 전환시 상태와 백 스택이 올바르게 저장
            //saveState = true
        }
        //동일한 항목을 선택할때 여러번 복사를 방지
        launchSingleTop = true
        //하단 탐색 항목 간 전환시 상태와 백 스택이 복원
        //restoreState = true
    }
}

fun NavHostController.onNavigateNext(
    nowScreen: Screens,
    argument: String? = null,
) {

    val destination =
       if (argument != null) nowScreen.routeByArgs(argument) else nowScreen.route

    this.navigateSingleTopTo(destination ?: "")

}