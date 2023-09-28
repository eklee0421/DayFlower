package com.nyang.dayFlower.presentation.flowerDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.nyang.dayFlower.R
import com.nyang.dayFlower.domain.model.flowerDetail.FlowerDetail
import com.nyang.dayFlower.ui.theme.Tertiary10
import kotlinx.coroutines.launch

@Composable
fun FlowerDetailScreen(
    viewModel : FlowerDetailViewModel = hiltViewModel()
) {

    FlowerDetailContent(flowerDetail = viewModel.flowerDetail.value)

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
private fun FlowerDetailContent(
    flowerDetail : FlowerDetail = FlowerDetail()
) {
    Scaffold(
        topBar = { FlowerDetailTopBar(month = flowerDetail.fMonth, day = flowerDetail.fDay)}
    ) {

        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        VerticalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.padding(it)) { page ->
            when(page){
                0 -> FlowerCard(flowerDetail = flowerDetail) {
                    coroutineScope.launch { pagerState.animateScrollToPage(1) } }
                1 -> FlowerOtherDetail(flowerDetail = flowerDetail){
                    coroutineScope.launch { pagerState.animateScrollToPage(0) } }
            }
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

        Divider(color = MaterialTheme.colorScheme.outlineVariant)

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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun FlowerCard(flowerDetail : FlowerDetail, movePage:()->Unit){

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
            .padding(16.dp)
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
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("${flowerDetail.flowNm}")
        Text("${flowerDetail.fEngNm}")
        Text("${flowerDetail.fSctNm}")
        Text("${flowerDetail.flowLang}")
    }
}

@Composable
private fun FlowerOtherDetail(flowerDetail: FlowerDetail, movePage: () -> Unit){

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        InfoContent(title = "이름", content = "${flowerDetail.flowNm} (${flowerDetail.fEngNm})")
        InfoContent(title = "학명", content = "${flowerDetail.fSctNm}")
        InfoContent(title = "꽃말", content = "${flowerDetail.flowLang}")
        InfoContent(title = "내용", content = "${flowerDetail.fContent}")
        InfoContent(title = "이용", content = "${flowerDetail.fUse}")
        InfoContent(title = "기르기", content = "${flowerDetail.fGrow}")
        InfoContent(title = "자생지", content = "${flowerDetail.fType}")
    }
}
@Composable
private fun InfoContent(title: String, content: String){
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, color = Tertiary10)
        Text(content)
    }
}

/*@Composable
private fun InfoImage(image1: String, image2: String, image3: String){
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){

    }
}*/

@Preview
@Composable
private fun FlowerDetailPreview() {
    FlowerDetailContent()
}

