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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun LockerScreen() {

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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
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

        }

        LockerContent()
    }
}

@Composable
private fun LockerContent() {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {


        Top()
    }
}

@Composable
private fun Top() {
    Box(modifier = Modifier.fillMaxWidth()) {

    }
}

@Preview
@Composable
private fun ContentPreview() {
    LockerScreen()
}