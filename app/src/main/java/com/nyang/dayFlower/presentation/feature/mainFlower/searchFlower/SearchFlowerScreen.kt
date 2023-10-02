package com.nyang.dayFlower.presentation.feature.mainFlower.searchFlower

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun SearchFlowerScreen(isShown: Boolean, onDismiss:()->Unit) {

    if(!isShown) return

    Dialog(onDismissRequest = { onDismiss() }) {
        SearchFlowerContent(onDismiss = onDismiss)
    }
}

@Composable
private fun SearchFlowerContent(onDismiss:()->Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        //상단
        Box{
            IconButton(onClick = { onDismiss() }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Rounded.ArrowBack , "")
            }
            Text("검색", modifier = Modifier.align(Alignment.Center))
            IconButton(onClick = { onDismiss() }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(Icons.Rounded.Search , "")
            }
        }

        //검색
        Row(){
            //스피너
            //텍스트필드
        }



    }
}