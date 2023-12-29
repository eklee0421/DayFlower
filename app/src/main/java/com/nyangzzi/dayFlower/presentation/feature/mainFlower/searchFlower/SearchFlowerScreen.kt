package com.nyangzzi.dayFlower.presentation.feature.mainFlower.searchFlower

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.Utils
import com.nyangzzi.dayFlower.ui.theme.Neutral40
import com.nyangzzi.dayFlower.ui.theme.Tertiary10

@Composable
fun SearchFlowerScreen(
    isShown: Boolean,
    onDismiss: () -> Unit,
    flowerList: List<FlowerDetail>,
    onSearch: (Int, String) -> Unit
) {

    if (!isShown) return

    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismiss() }) {
        SearchFlowerContent(onDismiss = onDismiss, flowerList = flowerList, onSearch = onSearch)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchFlowerContent(
    onDismiss: () -> Unit,
    flowerList: List<FlowerDetail>,
    onSearch: (Int, String) -> Unit
) {

    val type = remember { mutableStateOf(4) }
    val word = remember { mutableStateOf("사랑") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //상단
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { onDismiss() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Rounded.KeyboardArrowLeft, "")
            }
            Text("꽃 목록 검색", modifier = Modifier.align(Alignment.Center))
            IconButton(
                onClick = { onSearch(type.value, word.value) },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(Icons.Rounded.Search, "")
            }
        }

        //검색
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(16.dp)
        ) {
            val isExpanded = remember { mutableStateOf(false) }
            val typeList = listOf(
                1 to "꽃이름",
                2 to "학명",
                3 to "영문명",
                4 to "꽃말",
                5 to "내용",
                6 to "이용",
                7 to "기르기",
                8 to "자생지"
            )

            Button(
                onClick = { isExpanded.value = true },
                modifier = Modifier
                    .width(121.dp)
                    .fillMaxHeight()
            ) {
                Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        typeList.first { it.first == type.value }.second,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Rounded.KeyboardArrowDown, "")
                }
            }

            DropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false }) {
                for (typed in typeList) {
                    DropdownMenuItem(onClick = {
                        type.value = typed.first
                        isExpanded.value = false
                    }, text = { Text(typed.second) })
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = word.value,
                onValueChange = { word.value = it },
                trailingIcon = {
                    IconButton(onClick = {
                        word.value = ""
                    }) { Icon(Icons.Rounded.Clear, "") }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        if (flowerList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "조회된 리스트가 없습니다")
            }
        } else {
            val selectIndex = remember { mutableStateOf(-1) }
            Column {
                LazyColumn {
                    itemsIndexed(flowerList) { index, item ->
                        FlowerInfoSummary(item, index == selectIndex.value, index) {
                            if (selectIndex.value == it) selectIndex.value = -1
                            else selectIndex.value = it
                        }
                        if (index == selectIndex.value) {
                            FlowerInfoDetail(item)
                        }
                    }
                }
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("더보기")
                        Icon(Icons.Rounded.KeyboardArrowDown, "", tint = Neutral40)
                    }
                }
            }

        }


    }
}

@Composable
private fun FlowerInfoSummary(
    item: FlowerDetail,
    isDetail: Boolean,
    index: Int,
    selected: (Int) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .clickable { selected(index) }
        .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Utils.setImageUrl(item.imgUrl1))
                .crossfade(true)
                .build(),
            contentDescription = item.fileName1,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Text(item.flowNm.toString(), modifier = Modifier.weight(1f))
        Icon(if (isDetail) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown, "")
    }

}

@Composable
private fun FlowerInfoDetail(flowerDetail: FlowerDetail) {

    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        InfoContent(title = "학명", content = flowerDetail.fSctNm)
        InfoContent(title = "꽃말", content = flowerDetail.flowLang)
        InfoContent(title = "내용", content = flowerDetail.fContent)
        InfoContent(title = "이용", content = flowerDetail.fUse)
        InfoContent(title = "기르기", content = flowerDetail.fGrow)
        InfoContent(title = "자생지", content = flowerDetail.fType)
    }
}

@Composable
private fun InfoContent(title: String, content: String?) {
    content?.let {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, color = Tertiary10)
            Text(content)
        }
    }
}