package com.nyangzzi.dayFlower.presentation.feature.mainFlower

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nyangzzi.dayFlower.presentation.navigation.HomeNavGraph
import com.nyangzzi.dayFlower.presentation.navigation.Screens
import com.nyangzzi.dayFlower.presentation.navigation.navigateBottom
import com.nyangzzi.dayFlower.ui.theme.Gray5

@Composable
fun MainFlowerScreen(
    onNavigate: (Screens) -> Unit,
    viewModel: MainFlowerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            HomeNavGraph(
                navController
            )
        }

        FlowerBaseContent(navController = navController)
    }
}

@Composable
private fun FlowerBaseContent(navController: NavHostController) {

    val items = MainBottomNavItem.values()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        modifier = Modifier.height(80.dp),
        backgroundColor = Color.White,
    ) {
        items.map { item ->
            BottomNavigationItem(
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 16.dp),
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        tint = if (currentRoute == item.screen.route) MaterialTheme.colorScheme.primary else Gray5,
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .size(24.dp)
                            .padding(bottom = 2.dp)
                    )
                },
                label = {
                    Text(
                        stringResource(id = item.title),
                        color = if (currentRoute == item.screen.route) MaterialTheme.colorScheme.primary else Gray5,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigateBottom(item.screen.route)
                }
            )
        }
    }
}
