package com.nyangzzi.dayFlower.presentation.base.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.ui.theme.Gray1

@Composable
fun Badge(
    text: String,
          style: TextStyle =  LocalTextStyle.current,
          background: Color = Gray1,
    onClick : () -> Unit = {}) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        style = style,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(color = background,shape = RoundedCornerShape(32.dp))
            .clickable {
                onClick()
            }
            .padding(horizontal = 11.dp, vertical = 6.dp)
    )
}