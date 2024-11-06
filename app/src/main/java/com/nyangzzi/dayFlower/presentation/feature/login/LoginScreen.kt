package com.nyangzzi.dayFlower.presentation.feature.login

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.presentation.navigation.Screens
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun LoginScreen(
    onNavigate: (Screens) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.toastMsg) {
        uiState.toastMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(LoginEvent.ClearToastMsg)
        }
    }

    LaunchedEffect(key1 = uiState.isSuccessLogin) {
        if (uiState.isSuccessLogin) {
            viewModel.onEvent(LoginEvent.LoadApp)
        }
    }

    LaunchedEffect(key1 = uiState.isNextScreen) {
        if (uiState.isNextScreen) {
            onNavigate(Screens.MainFlower)
        }
    }

    LaunchedEffect(key1 = uiState.isBtnVisible) {
        if (uiState.isBtnVisible) viewModel.onEvent(LoginEvent.ClearDataStore)
    }

    LoginContent(onNavigate = onNavigate, uiState = uiState, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(
    onNavigate: (Screens) -> Unit,
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 56.dp, horizontal = 20.dp),
        containerColor = White,
        bottomBar = {
            if (uiState.isBtnVisible) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {


                    /* 보안 이슈로 기능 삭제
                   LoginButton(
                        icon = R.drawable.ic_login_kakao,
                        title = "카카오로 시작하기",
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFEE500),
                            contentColor = Color(0xFF000000),
                        ),
                        onClick = { onEvent(LoginEvent.KakaoLogin) }
                    )

                    LoginButton(
                        icon = R.drawable.ic_login_naver,
                        title = "네이버로 시작하기",
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF03C75A),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        onClick = { onEvent(LoginEvent.NaverLogin) }
                    )*/
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Text(
                        uiState.bottomMsg ?: "로그인 중입니다..",
                        color = Gray9,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_login_main),
                    modifier = Modifier
                        .size(104.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "icon"
                )
                Text(text = stringResource(id = R.string.app_name))
            }
        }
    }
}

@Composable
private fun LoginButton(
    @DrawableRes icon: Int,
    title: String,
    colors: ButtonColors,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        colors = colors,
        shape = RoundedCornerShape(size = 12.dp),
    ) {
        Image(
            painter = painterResource(id = icon),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(16.dp),
            contentDescription = "kakao"
        )
        Text(title)
    }
}

@Preview
@Composable
private fun Preview() {
    LoginContent(onEvent = {}, onNavigate = {}, uiState = LoginUiState())
}