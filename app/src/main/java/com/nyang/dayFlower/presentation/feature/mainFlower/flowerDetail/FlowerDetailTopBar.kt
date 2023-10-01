package com.nyang.dayFlower.presentation.feature.mainFlower.flowerDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nyang.dayFlower.presentation.feature.mainFlower.MainFlowerEvent
import java.time.LocalDate

@Composable
fun FlowerDetailTopBar(
    localDate: LocalDate?,
    onEvent: (MainFlowerEvent) -> Unit = {}
){

    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)){

        Divider(color = MaterialTheme.colorScheme.outlineVariant)

        Text("${localDate?.month?.value}월 ${localDate?.dayOfMonth}일",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { onEvent(MainFlowerEvent.ShowDatePicker) } )

        IconButton(onClick = { onEvent(MainFlowerEvent.SearchPrevMainFlower) },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterStart)) {
            Icon(imageVector= Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "")
        }

        IconButton(onClick = { onEvent(MainFlowerEvent.SearchNextMainFlower)  },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterEnd)) {
            Icon(imageVector= Icons.Rounded.KeyboardArrowRight,
                contentDescription = "")
        }
    }
}