package com.nyang.dayFlower.presentation.feature.mainFlower.flowerDetail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyang.dayFlower.R
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.base.Utils

@Composable
fun FlowerCard(flowerDetail : FlowerDetail, movePage:()->Unit){

    val cardState = remember{ mutableStateOf(true) }

    Column(modifier = Modifier
        .padding(16.dp)
    ){
        Box(modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.White)
            .clickable { cardState.value = !cardState.value }
        ){
            if(cardState.value) FlowerCardFront(flowerDetail=flowerDetail)
            else FlowerCardBack(flowerDetail=flowerDetail)
        }

        Spacer(modifier = Modifier.size(16.dp))

        IconButton(onClick = { movePage() },
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally)) {
            Image(painter = painterResource(id = R.drawable.ic_double_arrow_down),
                contentDescription = "down_arrow")
        }
    }


}

@Composable
private fun FlowerCardFront(flowerDetail : FlowerDetail) {
    Box(modifier = Modifier.fillMaxSize()){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Utils.setImageUrl(flowerDetail.imgUrl1))
                .crossfade(true)
                .build(),
            contentDescription = flowerDetail.fileName1,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        CardTitle(flowerDetail = flowerDetail)
    }

}

@Composable
private fun FlowerCardBack(flowerDetail : FlowerDetail){
    Box(modifier = Modifier.fillMaxSize()){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Utils.setImageUrl(flowerDetail.imgUrl2))
                .crossfade(true)
                .build(),
            contentDescription = flowerDetail.fileName2,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        CardTitle(flowerDetail = flowerDetail)
    }
}

@Composable
private fun CardTitle(flowerDetail : FlowerDetail) {
    Column(modifier = Modifier.fillMaxHeight().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom)
    ) {
        Text("${flowerDetail.flowNm} / ${flowerDetail.fEngNm}", color = Color.White)
        Text("${flowerDetail.fSctNm}", color = Color.White)
        Text("${flowerDetail.flowLang}", color = Color.White)
    }
}