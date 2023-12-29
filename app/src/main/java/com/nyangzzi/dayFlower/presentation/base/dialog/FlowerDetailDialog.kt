package com.nyangzzi.dayFlower.presentation.base.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun FlowerDetailDialog(flowerDetail: FlowerDetail, onDismiss: () -> Unit) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        FlowerDetailContent(flowerDetail = flowerDetail, onDismiss = onDismiss)
    }

}

@Composable
private fun FlowerDetailContent(flowerDetail: FlowerDetail, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Top(onDismiss = onDismiss)
    }
}

@Composable
private fun Top(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        IconButton(onClick = onDismiss, modifier = Modifier.size(56.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_close), contentDescription = null)
        }
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun Body(flower: FlowerDetail) {

}