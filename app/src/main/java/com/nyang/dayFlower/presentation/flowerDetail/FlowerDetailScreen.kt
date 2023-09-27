package com.nyang.dayFlower.presentation.flowerDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlowerDetailContent(
    flowerDetail : FlowerDetail = FlowerDetail()
) {
    Scaffold(
        topBar = { FlowerDetailTopBar(month = flowerDetail.fMonth, day = flowerDetail.fDay)}
    ) {
        Box(modifier = Modifier.padding(it)){
            FlowerCard(flowerDetail=flowerDetail)
        }

    }
}

@Composable
private fun FlowerDetailTopBar(
    month : Int?,
    day : Int?
){
    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)){

        Divider(color = MaterialTheme.colorScheme.outline)

        Text("${month}월 ${day}일",
            modifier = Modifier.align(Alignment.Center))

        IconButton(onClick = {  },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterStart)) {
            Icon(imageVector= Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "")
        }

        IconButton(onClick = {  },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterEnd)) {
            Icon(imageVector= Icons.Rounded.KeyboardArrowRight,
                contentDescription = "")
        }
    }
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


    }


}

@Composable
private fun FlowerCardBack(flowerDetail : FlowerDetail){
    Box(modifier = Modifier.fillMaxSize()) {
        Text("${flowerDetail.fContent}")
    }
}

@Composable
private fun FlowerOtherDetail(){

}

@Preview
@Composable
private fun FlowerDetailPreview() {
    FlowerDetailContent()
}

