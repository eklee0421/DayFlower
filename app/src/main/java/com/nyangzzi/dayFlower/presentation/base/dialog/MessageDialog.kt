package com.nyangzzi.dayFlower.presentation.base.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray3
import com.nyangzzi.dayFlower.ui.theme.Gray6
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun MessageDialog(
    title: String? = null,
    content: String? = null,
    isShowContentIcon: Boolean = true,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.background(
                    color = White,
                    shape = RoundedCornerShape(16.dp)
                )
            ) {
                DialogView(
                    title = title,
                    content = content, isShowContentIcon = isShowContentIcon
                )

                Divider(
                    color = Gray3, modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )

                BottomButton(
                    confirmText = confirmText,
                    onConfirm = onConfirm,
                    onDismiss = onDismiss
                )
            }
        }
    }

}

@Composable
private fun DialogView(title: String?, content: String?, isShowContentIcon: Boolean) {


    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 26.dp)
    ) {

        title?.let {
            Text(text = it, style = MaterialTheme.typography.titleMedium, color = Gray11)
        }

        content?.let {

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                // verticalAlignment = Alignment.CenterVertically,

                modifier = Modifier.fillMaxWidth()
            ) {
                if (isShowContentIcon) Image(
                    painter = painterResource(id = R.drawable.ic_error_info),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(top = 4.dp),
                    colorFilter = ColorFilter.tint(Gray6)
                )
                Text(
                    text = content,
                    color = Gray6,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }


    }
}

@Composable
fun BottomButton(confirmText: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TextButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(bottomStart = 16.dp),
            onClick = { onDismiss() }) {
            Text(text = "취소", color = Gray9, style = MaterialTheme.typography.titleSmall)
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), color = Gray3
        )
        TextButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(bottomEnd = 16.dp),
            onClick = { onConfirm() }) {
            Text(text = confirmText, color = Gray9, style = MaterialTheme.typography.titleSmall)
        }
    }
}