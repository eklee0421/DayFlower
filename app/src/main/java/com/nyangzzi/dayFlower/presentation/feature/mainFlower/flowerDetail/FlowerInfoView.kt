package com.nyangzzi.dayFlower.presentation.feature.mainFlower.flowerDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.ui.theme.Tertiary10

@Composable
fun FlowerInfoView(flowerDetail: FlowerDetail, movePage: () -> Unit) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InfoContent(title = "이름", content = "${flowerDetail.flowNm} (${flowerDetail.fEngNm})")
        InfoContent(title = "학명", content = "${flowerDetail.fSctNm}")
        InfoContent(title = "꽃말", content = "${flowerDetail.flowLang}")
        InfoContent(title = "내용", content = "${flowerDetail.fContent}")
        InfoContent(title = "이용", content = "${flowerDetail.fUse}")
        InfoContent(title = "기르기", content = "${flowerDetail.fGrow}")
        InfoContent(title = "자생지", content = "${flowerDetail.fType}")
    }
}

@Composable
private fun InfoContent(title: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, color = Tertiary10)
        Text(content)
    }
}