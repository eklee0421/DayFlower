package com.nyang.dayFlower.presentation.feature.mainFlower.flowerMonth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.base.component.BaseFixedGrid

@Composable
fun FlowerCalendar(flowerMonth: List<FlowerDetail>) {
    BaseFixedGrid(gridList = flowerMonth.sortedWith(compareBy<FlowerDetail> { it.fMonth}.thenBy { it.fDay }), splitSize = 7) {flowerDetail->
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            (flowerDetail as FlowerDetail)
            Spacer(modifier = Modifier.fillMaxSize().padding(4.dp).background(Color.Yellow, RoundedCornerShape(16.dp)))
            Text(text= flowerDetail.fDay.toString())
        }
    }
}

@Composable
private fun FlowerCalendarImage(){

}
