package com.nyang.dayFlower.presentation.feature.mainFlower

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.nyang.dayFlower.R
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.feature.mainFlower.flowerDetail.FlowerDetailView
import com.nyang.dayFlower.presentation.feature.mainFlower.flowerMonth.FlowerMonthView
import com.nyang.dayFlower.presentation.navigation.HomeNavGraph
import com.nyang.dayFlower.presentation.navigation.Screens
import com.nyang.dayFlower.presentation.navigation.navigateSingleTopTo
import com.nyang.dayFlower.ui.theme.Gray5
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFlowerScreen(
    onNavigate: (Screens) -> Unit,
    viewModel: MainFlowerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    /*SearchFlowerScreen(isShown = uiState.isSearch,
        onDismiss = {viewModel.onEvent(MainFlowerEvent.IsSearchDialog(false))},
        flowerList = uiState.flowerList,
        onSearch = { type, word-> viewModel.onEvent(MainFlowerEvent.SearchFlowerList(type,word)) }
    )*/

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


    /*FlowerBaseContent(
        flowerDetail = uiState.flowerDetail,
        flowerMonth= uiState.flowerMonth,
        localDate = uiState.localDate,
        isDatePicker = uiState.isDatePicker,
        isCalendar = uiState.isCalendar,
        onEvent = viewModel::onEvent
        )*/

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
                    navController.navigateSingleTopTo(item.screen.route)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlowerBaseContents(
    flowerDetail: FlowerDetail = FlowerDetail(),
    flowerMonth: List<FlowerDetail> = emptyList(),
    localDate: LocalDate = LocalDate.now(),
    isDatePicker: Boolean = false,
    isCalendar: Boolean = false,
    onEvent: (MainFlowerEvent) -> Unit = {}
) {

    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .height(56.dp)
        ) {
            Text("하루, 꽃", modifier = Modifier.align(Alignment.Center))

            IconButton(
                onClick = { onEvent(MainFlowerEvent.IsSearchDialog(true)) },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Rounded.Search, "")
            }

            if (!isCalendar) {
                IconButton(onClick = {
                    onEvent(MainFlowerEvent.IsChangeView(isCalendar = true))
                }, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null
                    )
                }
            }

        }

    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (isCalendar) {
                FlowerMonthView(
                    flowerMonth = flowerMonth,
                    localDate = localDate,
                    onEvent = onEvent
                )
            } else {
                FlowerDetailView(
                    flowerDetail = flowerDetail,
                    localDate = localDate,
                    isDatePicker = isDatePicker,
                    onEvent = onEvent
                )
            }
        }
    }
}
