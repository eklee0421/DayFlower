package com.nyangzzi.dayFlower.presentation.feature.locker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseUser
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun LockerScreen() {

    val viewModel: LockerViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {

        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.img_locker_top_background),
            contentDescription = null
        )

        LockerContent(uiState = uiState)
    }
}

@Composable
private fun LockerContent(uiState: LockerUiState) {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {


        Top(user = uiState.user)
    }
}

@Composable
private fun Top(user: FirebaseUser?) {
    Box(modifier = Modifier.fillMaxWidth()) {

        Image(
            painter = painterResource(id = R.drawable.ic_locker_left_flower),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 12.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_locker_right_flower),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 55.dp)
        )

        Text(
            text = "${user?.displayName}님의 기분 좋은 하루가\n차곡 차곡 쌓이고 있어요!",
            style = MaterialTheme.typography.headlineSmall,
            color = Gray11,
            modifier = Modifier.padding(top = 75.dp, start = 20.dp)
        )
    }
}

@Preview
@Composable
private fun ContentPreview() {
    LockerScreen()
}