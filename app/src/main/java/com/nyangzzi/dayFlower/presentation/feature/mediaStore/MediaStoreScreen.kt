package com.nyangzzi.dayFlower.presentation.feature.mediaStore

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.nyangzzi.dayFlower.presentation.base.dialog.BottomButton
import com.nyangzzi.dayFlower.presentation.base.util.noRippleClickable
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray3
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun MediaStoreScreen(isShown: Boolean, onDismiss: () -> Unit) {

    if (isShown) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            MediaStoreView(onDismiss = onDismiss)
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MediaStoreView(onDismiss: () -> Unit) {

    val context = LocalContext.current

    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) listOf(
            Manifest.permission.READ_MEDIA_IMAGES
        )
        else listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable {
                onDismiss()
            },
    ) {
        PermissionsRequired(
            multiplePermissionsState = multiplePermissionsState,
            //허가되지 않았거나 요청하였지만 사용자가 하나라도 거부한 경우
            permissionsNotGrantedContent = {
                PermissionDialog(
                    modifier = Modifier.align(Alignment.Center),
                    context = context,
                    multiplePermissionsState = multiplePermissionsState,
                    onDismiss = onDismiss
                )
            },
            //사용자가 다시 묻지 않음을 한 경우
            permissionsNotAvailableContent = {
                PermissionUserDialog(
                    modifier = Modifier.align(Alignment.Center),
                    context = context,
                    onDismiss = onDismiss
                )
            }
        ) {
            GalleryView(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
private fun GalleryView(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = White,
            )
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.padding(vertical = 56.dp)) {
            Text("개발중입니다")
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionDialog(
    modifier: Modifier,
    context: Context,
    multiplePermissionsState: MultiplePermissionsState,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(16.dp)
            )
            .then(modifier)
    ) {

        Box(
            modifier = Modifier.padding(horizontal = 26.dp, vertical = 56.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "갤러리 접근 권한이 필요합니다",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium, color = Gray11,
                textAlign = TextAlign.Center
            )
        }
        Divider(
            color = Gray3, modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        BottomButton(
            confirmText = "확인",
            onConfirm = { multiplePermissionsState.launchMultiplePermissionRequest() },
            onDismiss = {
                onDismiss()
                Toast.makeText(context, "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
        )
    }
}


@Composable
private fun PermissionUserDialog(modifier: Modifier, context: Context, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(16.dp)
            )
            .then(modifier)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 26.dp, vertical = 56.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "다시 묻지 않음 처리되어 앱 정보 화면의 권한설정에서 권한 허가가 필요 합니다",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium, color = Gray11,
                textAlign = TextAlign.Center
            )
        }
        Divider(
            color = Gray3, modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

        BottomButton(
            confirmText = "확인",
            onConfirm = {
                val appDetailIntent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:${context.packageName}")
                ).apply {
                    addCategory(Intent.CATEGORY_DEFAULT)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(appDetailIntent)
            },
            onDismiss = {
                onDismiss()
                Toast.makeText(context, "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
        )

    }
}