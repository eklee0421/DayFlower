package com.nyang.dayFlower.presentation.flowerDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nyang.dayFlower.domain.model.flowerDetail.FlowerDetail

@Composable
fun FlowerDetailScreen(
    viewModel : FlowerDetailViewModel = hiltViewModel()
) {
    FlowerDetailContent(flowerDetail = viewModel.flowerDetail.value)
}

@Composable
private fun FlowerDetailContent(
    flowerDetail : FlowerDetail = FlowerDetail()
) {
    FlowerCard(flowerDetail=flowerDetail)
}

@Composable
private fun FlowerCard(flowerDetail : FlowerDetail){

    val selectType = remember{ mutableStateOf(true) }

    Box(modifier = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { selectType.value = !selectType.value }
    ){
        if(selectType.value) FlowerCardFront(flowerDetail=flowerDetail)
        else FlowerCardBack(flowerDetail=flowerDetail)
    }


}

@Composable
private fun FlowerCardFront(flowerDetail : FlowerDetail){

    Box(){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(flowerDetail.imgUrl1)
                .build(),
            contentDescription = flowerDetail.fileName1,
            modifier = Modifier.fillMaxSize())

        Text("${flowerDetail.fMonth}월 ${flowerDetail.fDay}일",
            color = Color.Red,
            modifier = Modifier.align(Alignment.BottomStart))
    }


}

@Composable
private fun FlowerCardBack(flowerDetail : FlowerDetail){
    Text("${flowerDetail.fContent}")
}

@Preview
@Composable
private fun FlowerDetailPreview() {
    FlowerDetailContent()
}

