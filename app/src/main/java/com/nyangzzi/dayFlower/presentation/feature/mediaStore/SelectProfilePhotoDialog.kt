package com.nyangzzi.dayFlower.presentation.feature.mediaStore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun SelectProfilePhotoDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    deletePhoto: () -> Unit,
    syncSnsPhoto: () -> Unit,
    selectGallery: () -> Unit,
) {

    if (isShow) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = White, shape = RoundedCornerShape(16.dp)
                        )
                        .padding(24.dp),
                ) {
                    Content(
                        onDismiss = onDismiss,
                        deletePhoto = deletePhoto,
                        syncSnsPhoto = syncSnsPhoto,
                        selectGallery = selectGallery
                    )
                }
            }
        }
    }

}

@Composable
private fun Content(
    onDismiss: () -> Unit,
    deletePhoto: () -> Unit,
    syncSnsPhoto: () -> Unit,
    selectGallery: () -> Unit,
) {

    val photoType = listOf(
        Pair("갤러리에서 선택", selectGallery),
        Pair("SNS에서 가져오기", syncSnsPhoto),
        Pair("프로필 사진 삭제", deletePhoto)
    )

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "프로필 사진 선택",
                style = MaterialTheme.typography.titleSmall,
                color = Gray11,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onDismiss) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                )
            }
        }
        photoType.map {
            TextButton(onClick = {
                onDismiss()
                it.second()
            }) {
                Text(
                    text = it.first,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,

                    color = Gray9
                )
            }
        }
    }

}