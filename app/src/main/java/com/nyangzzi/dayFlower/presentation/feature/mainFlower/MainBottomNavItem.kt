package com.nyangzzi.dayFlower.presentation.feature.mainFlower

import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.presentation.navigation.Screens

enum class MainBottomNavItem(val title: Int, val icon: Int, val screen: Screens) {
    Home(title = R.string.home, icon = R.drawable.ic_bottom_home, screen = Screens.Home),
    Calendar(title = R.string.calendar, icon = R.drawable.ic_bottom_calendar, screen = Screens.Calendar),
    Search(title = R.string.search, icon = R.drawable.ic_bottom_search, screen = Screens.Search),
    Locker(title = R.string.locker, icon = R.drawable.ic_bottom_locker, screen = Screens.Locker),
    Profile(title = R.string.profile, icon = R.drawable.ic_bottom_profile, screen = Screens.Profile)
}