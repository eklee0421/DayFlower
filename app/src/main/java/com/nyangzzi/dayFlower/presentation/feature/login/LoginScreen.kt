package com.nyangzzi.dayFlower.presentation.feature.login

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    LoginContent(login = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(login: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 56.dp, horizontal = 20.dp),
        containerColor = White,
        bottomBar = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFEE500),
                        contentColor = Color(0xFF000000),
                    ),
                    shape = RoundedCornerShape(size = 12.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_login_kakao),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(16.dp),
                        contentDescription = "kakao"
                    )
                    Text("카카오로 시작하기")
                }

                Button(
                    onClick = { },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03C75A),
                        contentColor = Color(0xFFFFFFFF),
                    ),
                    shape = RoundedCornerShape(size = 12.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_login_naver),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(16.dp),
                        contentDescription = "kakao"
                    )
                    Text("네이버로 시작하기")
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