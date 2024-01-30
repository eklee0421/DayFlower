package com.nyangzzi.dayFlower.presentation.feature.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.domain.model.common.PLATFORM_KAKAO
import com.nyangzzi.dayFlower.presentation.base.util.Utils
import com.nyangzzi.dayFlower.presentation.navigation.Screens
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray3
import com.nyangzzi.dayFlower.ui.theme.Gray4
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray6
import com.nyangzzi.dayFlower.ui.theme.Gray8
import com.nyangzzi.dayFlower.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(onNavigate: (Screens) -> Unit) {

    val viewModel: ProfileViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(uiState.toastMsg) {
        uiState.toastMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(ProfileEvent.ClearToastMsg)
        }
    }

    LaunchedEffect(uiState.isLogout) {
        if (uiState.isLogout) {
            onNavigate(Screens.Login)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        ProfileContent(uiState = uiState, onEvent = viewModel::onEvent)
    }
}

@Composable
private fun ProfileContent(uiState: ProfileUiState, onEvent: (ProfileEvent) -> Unit) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        User(name = uiState.nickname, uiState.imgUrl, uiState.email, uiState.platform) {
            onEvent(ProfileEvent.UpdateUserName(it))
        }
        AppInfo(onEvent = onEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun User(
    name: String,
    urlImg: String?,
    email: String,
    platform: String,
    updateName: (String) -> Unit
) {
    var isEditName by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${name}님",
                style = MaterialTheme.typography.titleSmall,
                color = Gray11
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(106.dp),
            contentAlignment = Alignment.Center
        ) {

            if (urlImg.isNullOrEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_none_profile_img),
                    contentDescription = null
                )

            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Utils.setImageUrl(urlImg))
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color(0xFFAFB1B6), shape = CircleShape),
                    placeholder = painterResource(id = R.drawable.ic_loading_image)
                )
            }


        }

        Text(
            "프로필 설정",
            style = MaterialTheme.typography.labelMedium,
            color = Gray11
        )

        Spacer(modifier = Modifier.height(11.dp))

        val textFieldColor = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            containerColor = Color.Transparent,
            cursorColor = Gray4,
            placeholderColor = Gray5,
            disabledTextColor = Gray6,
            textColor = Gray11
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(color = Gray1, shape = RoundedCornerShape(10.dp))
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                val scope = rememberCoroutineScope()

                LaunchedEffect(name) {
                    editName = name
                    isEditName = false
                }

                TextField(
                    value = editName,
                    onValueChange = { editName = it },
                    //readOnly = !isEditName,
                    enabled = isEditName,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    colors = textFieldColor,
                    placeholder = {
                        Text(text = "닉네임을 입력하세요", style = MaterialTheme.typography.bodyMedium)
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .weight(1f),
                )

                Image(
                    painter = painterResource(
                        id = if (isEditName) R.drawable.ic_close else R.drawable.ic_edit_pen
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(24.dp)
                        .clickable {
                            scope.launch {
                                isEditName = !isEditName
                                delay(100)
                                if (!isEditName) editName = name
                                else focusRequester.requestFocus()
                            }
                        }
                        .padding(2.dp)
                )


            }

            Divider(color = Gray3)

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Image(
                    painter = painterResource(
                        id =
                        if (platform == PLATFORM_KAKAO) R.drawable.ic_login_kakao
                        else R.drawable.ic_login_naver
                    ), contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = if (platform == PLATFORM_KAKAO) Color(0xFFFEE500)
                            else Color(0xFF03C75A),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(6.dp)
                )

                Text(
                    text = email, style = MaterialTheme.typography.bodyMedium,
                    color = Gray6
                )
            }


        }

        if (isEditName) {

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    updateName(editName)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = editName.isNotBlank() && editName != name,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "저장",
                    style = MaterialTheme.typography.titleLarge,
                    color = White
                )
            }
        }
    }
}

@Composable
private fun AppInfo(onEvent: (ProfileEvent) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ProfileBtn(text = "앱 정보", onClick = {})
        ProfileBtn(text = "로그아웃", onClick = {
            onEvent(ProfileEvent.Logout)
        })
        ProfileBtn(text = "앱 탈퇴", onClick = {})
    }
}

@Composable
private fun ProfileBtn(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Gray8,
            containerColor = Gray1
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text, style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

    }

}