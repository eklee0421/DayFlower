package com.nyang.dayFlower.presentation.base.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.base.Utils
import com.nyang.dayFlower.ui.theme.Gray1
import com.nyang.dayFlower.ui.theme.White

@Composable
fun FlowerCard(flower: FlowerDetail) {


    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp))
    ) {

        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Utils.setImageUrl(flower.imgUrl1))
                    .crossfade(true)
                    .build(),
                contentDescription = flower.fileName1,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )


            /*Row(horizontalArrangement = ){

            }*/

        }
    }
}

/*@Composable
private fun */

@Preview
@Composable
fun PreviewFlowerCard() {
    FlowerCard(flower = FlowerDetail())
}