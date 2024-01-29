package com.nyangzzi.dayFlower.presentation.base.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun Badge(
    text: String,
    style: TextStyle = LocalTextStyle.current,
    background: Color = Gray1,
    onClick: () -> Unit = {}
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        style = style,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(color = background, shape = RoundedCornerShape(32.dp))
            .clickable {
                onClick()
            }
            .padding(horizontal = 11.dp, vertical = 6.dp)
    )
}

/**
 * 삭제 가능한 뱃지
 */
@Composable
fun DeletedBadge(
    text: String,
    style: TextStyle = LocalTextStyle.current,
    background: Color = White,
    onDeleted: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = background, shape = RoundedCornerShape(32.dp))
            .border(width = 1.dp, color = Gray1, shape = RoundedCornerShape(32.dp))
            .padding(horizontal = 11.dp)
    ) {
        Box(modifier = Modifier.height(26.dp), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = Gray9,
                style = style,
            )
        }
        Image(painter = painterResource(id = R.drawable.ic_close_circle),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onDeleted()
                })

    }


}