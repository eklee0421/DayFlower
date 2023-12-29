package com.nyangzzi.dayFlower.presentation.base.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BaseFixedGrid(
    modifier: Modifier = Modifier,
    gridList: List<Any>,
    splitSize: Int,
    isOutlined: Boolean = false,
    verticalArrangement: Dp = 0.dp,
    horizontalArrangement: Dp = 0.dp,
    content: @Composable (Any) -> Unit
) {
    val splitList = gridList.chunked(splitSize)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalArrangement)
    ) {
        for (i in splitList.indices) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(horizontalArrangement),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                for (j in 0 until splitSize) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (j < splitList[i].size) {
                            content(splitList[i][j])
                        }
                    }
                }
            }
        }
    }
}