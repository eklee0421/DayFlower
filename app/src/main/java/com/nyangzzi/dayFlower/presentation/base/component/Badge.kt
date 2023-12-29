package com.nyangzzi.dayFlower.presentation.base.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.ui.theme.Gray1

@Composable
fun Badge(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .background(color = Gray1, shape = RoundedCornerShape(32.dp))
            .padding(horizontal = 11.dp, vertical = 6.dp)
    )
}