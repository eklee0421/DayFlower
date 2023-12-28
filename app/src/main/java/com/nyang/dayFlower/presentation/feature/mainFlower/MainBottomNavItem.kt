package com.nyang.dayFlower.presentation.feature.mainFlower

import com.nyang.dayFlower.R
import com.nyang.dayFlower.presentation.navigation.Screens

enum class MainBottomNavItem(val title: Int, val icon: Int, val route: Screens) {
    Home(title = R.string.home, icon = R.drawable.ic_bottom_home, route = Screens.Home),
    Search(title = R.string.search, icon = R.drawable.ic_bottom_search, route = Screens.Search),
    Locker(title = R.string.locker, icon = R.drawable.ic_bottom_locker, route = Screens.Locker),
    Profile(title = R.string.profile, icon = R.drawable.ic_bottom_profile, route = Screens.Profile)
}