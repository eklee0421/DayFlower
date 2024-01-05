package com.nyangzzi.dayFlower.presentation.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray3
import com.nyangzzi.dayFlower.ui.theme.Gray4
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        ProfileContent()
    }
}

@Composable
private fun ProfileContent() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        User(name = "000")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun User(name: String) {
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
            containerColor = Color.Transparent,
            cursorColor = Gray4,
            placeholderColor = Gray5
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(color = Gray1, shape = RoundedCornerShape(10.dp))
        ) {

            TextField(
                value = name,
                onValueChange = {},
                singleLine = true,
                colors = textFieldColor,
                placeholder = {
                    Text(text = "닉네임을 입력하세요")
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Divider(color = Gray3)

            TextField(
                value = "",
                onValueChange = {},
                singleLine = true,
                placeholder = {
                    Text(text = "소개를 입력하세요")
                },
                colors = textFieldColor,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
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
