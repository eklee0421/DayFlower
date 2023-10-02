package com.nyang.dayFlower.presentation.feature.mainFlower.searchFlower

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nyang.dayFlower.domain.model.common.FlowerDetail

@Composable
fun SearchFlowerScreen(isShown: Boolean, onDismiss:()->Unit, flowerList: List<FlowerDetail>,onSearch: (Int,String) -> Unit) {

    if(!isShown) return

    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismiss() }) {
        SearchFlowerContent(onDismiss = onDismiss, flowerList=flowerList, onSearch=onSearch)
    }
}

@Composable
private fun SearchFlowerContent(onDismiss:()->Unit, flowerList: List<FlowerDetail>, onSearch:(Int,String)->Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        //상단
        Box(modifier = Modifier.fillMaxWidth()){
            IconButton(onClick = { onDismiss() }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Rounded.KeyboardArrowLeft , "")
            }
            Text("꽃 목록 검색", modifier = Modifier.align(Alignment.Center))
            IconButton(onClick = { onSearch(4,"사랑") }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(Icons.Rounded.Search , "")
            }
        }

        //검색
        Row(){
            //스피너
            //텍스트필드
        }

        if(flowerList.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = "조회된 리스트가 없습니다")
            }
        }
        else{
            val selectIndex = remember { mutableStateOf(-1) }
            Column {
                LazyColumn {
                    itemsIndexed(flowerList){index, item ->
                        FlowerInfoSummary(item)
                        if(index == selectIndex.value){
                            FlowerInfoDetail(item)
                        }
                    }
                }
                IconButton(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
                        Text("더보기")
                        Icon(Icons.Rounded.KeyboardArrowDown,"")
                    }
                }
            }

        }


    }
}

@Composable
private fun FlowerInfoSummary(item: FlowerDetail){
    Text(item.flowNm.toString())
}
@Composable
private fun FlowerInfoDetail(item: FlowerDetail){
    Text(item.fEngNm.toString())
}