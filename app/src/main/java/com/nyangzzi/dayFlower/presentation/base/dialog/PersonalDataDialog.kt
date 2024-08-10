package com.nyangzzi.dayFlower.presentation.base.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray3
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun PersonalDataDialog(isShow: Boolean, onDismiss: () -> Unit) {

    if (isShow) {
        Dialog(onDismiss = onDismiss)
    }
}


@Composable
private fun Dialog(
    onDismiss: () -> Unit,
) {

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties()
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "개인정보 처리 방침",
                        style = MaterialTheme.typography.titleLarge,
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

                Content()
            }
        }
    }

}

@Composable
private fun Content() {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val section1 = SectionData(
            title = "개인정보의 수집 항목 및 수집 방법", contents = listOf(
                ArticleData(
                    title = "수집 항목", contents = listOf(
                        DetailData(
                            content = "하루 한 송이 앱(이하 회사)은 다음과 같은 개인정보를 수집합니다", detail = listOf(
                                "필수 항목: 이메일 주소",
                                "선택 항목: 별명, 프로필 사진",
                                "자동 수집 항목: 서비스 이용 기록, 접속 로그, 쿠키, 접속 IP 정보",
                                "추가 수집 항목: 갤러리 접근 권한(사진 및 미디어 파일)"
                            )
                        )
                    )
                ), ArticleData(
                    title = "수집 방법", contents = listOf(
                        DetailData(content = "SNS 연동 로그인 시 사용자가 제공하는 정보"),
                        DetailData(content = "앱 사용 과정에서 자동으로 생성되어 수집되는 정보"),
                        DetailData(content = "사용자의 갤러리 접근 권한을 통해 수집되는 정보")
                    )
                )
            )
        )

        val section2 = SectionData(
            title = "개인정보의 수집 및 이용 목적", contents = listOf(
                ArticleData(
                    title = "수집 목적", contents = listOf(
                        DetailData(content = "회원 관리: SNS 연동을 통한 본인 확인, 개인 식별"),
                        DetailData(content = "서비스 제공: 앱 내 로그인 정보 표시 및 사용자 맞춤 서비스 제공"),
                        DetailData(content = "데이터 관리: 수집된 개인정보는 Firebase에 등록하여 안전하게 관리됩니다."),
                        DetailData(content = "갤러리 접근: 사용자 요청에 따른 사진 및 미디어 파일의 업로드 기능 제공")
                    )
                )
            )
        )

        val section3 = SectionData(
            title = "개인정보의 보유 및 이용 기간", contents = listOf(
                ArticleData(
                    title = "보유 기간", contents = listOf(
                        DetailData(content = "회원 탈퇴 시까지 보유 및 이용"),
                        DetailData(content = "단, 관련 법령에 의해 보존할 필요가 있는 경우에는 해당 법령에서 정한 기간 동안 보존")
                    )
                ), ArticleData(
                    title = "관련 법령에 따른 보유 기간", contents = listOf(
                        DetailData(content = "계약 또는 청약철회 등에 관한 기록: 5년"),
                        DetailData(content = "소비자의 불만 또는 분쟁처리에 관한 기록: 3년"),
                        DetailData(content = "웹사이트 방문기록: 3개월")
                    )
                )
            )
        )

        val section4 = SectionData(
            title = "개인정보의 제3자 제공", contents = listOf(
                ArticleData(
                    title = "제3자 제공", contents = listOf(
                        DetailData(content = "회사는 사용자의 개인정보를 원칙적으로 외부에 제공하지 않습니다."),
                        DetailData(content = "다만, 아래의 경우에는 예외로 합니다:"),
                        DetailData(content = "사용자가 사전에 동의한 경우"),
                        DetailData(content = "법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우")
                    )
                )
            )
        )

        val section5 = SectionData(
            title = "개인정보 처리의 위탁", contents = listOf(
                ArticleData(
                    title = "위탁 관리", contents = listOf(
                        DetailData(content = "회사는 개인정보 업무 처리를 위해 Firebase에 개인정보를 위탁하여 관리하고 있습니다."),
                        DetailData(content = "Firebase는 Google에서 제공하는 서비스로, 개인정보의 안전한 관리 및 보호를 위해 기술적·관리적 보호조치를 시행하고 있습니다.")
                    )
                )
            )
        )

        val section6 = SectionData(
            title = "정보주체의 권리와 그 행사 방법", contents = listOf(
                ArticleData(
                    title = "사용자 권리", contents = listOf(
                        DetailData(content = "사용자는 언제든지 개인정보 열람, 정정, 삭제, 처리정지 요구 등의 권리를 행사할 수 있습니다.")
                    )
                ), ArticleData(
                    title = "행사 방법", contents = listOf(
                        DetailData(content = "서면, 전화, 전자우편 등을 통하여 권리 행사 가능"),
                        DetailData(content = "회사는 지체 없이 조치하겠습니다.")
                    )
                )
            )
        )

        val section7 = SectionData(
            title = "개인정보의 안전성 확보 조치", contents = listOf(
                ArticleData(
                    title = "보안 조치", contents = listOf(
                        DetailData(content = "관리적 조치: 내부관리계획 수립∙시행, 정기적 직원 교육"),
                        DetailData(content = "기술적 조치: 개인정보처리시스템 등의 접근권한 관리, 고유식별정보 등의 암호화"),
                        DetailData(content = "물리적 조치: 전산실, 자료 보관실 등의 접근 통제")
                    )
                )
            )
        )

        val section8 = SectionData(
            title = "개인정보 보호 책임자", contents = listOf(
                ArticleData(
                    title = "책임자 정보", contents = listOf(
                        DetailData(content = "회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 사용자의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호 책임자를 지정하고 있습니다."),
                        DetailData(content = "성명: 이은경"),
                        DetailData(content = "연락처: ekyi0421@gmail.com")
                    )
                )
            )
        )

        val section9 = SectionData(
            title = "개인정보 처리 방침의 변경", contents = listOf(
                ArticleData(
                    title = "변경 공지", contents = listOf(
                        DetailData(content = "이 개인정보 처리 방침은 2024년 7월 31일부터 적용됩니다."),
                        DetailData(content = "법령, 정책 또는 보안 기술의 변경에 따라 내용의 추가, 삭제 및 수정이 있을 경우에는 변경 사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.")
                    )
                )
            )
        )


        val sectionList = listOf(
            section1,
            section2,
            section3,
            section4,
            section5,
            section6,
            section7,
            section8,
            section9,
        )

        sectionList.mapIndexed { index, section ->
            if (index != 0) Divider(color = Gray3, modifier = Modifier.width(56.dp))
            SectionContent(title = "제${index + 1}조 (${section.title})", contents = section.contents)
        }

    }


}


@Composable
private fun SectionContent(title: String?, contents: List<ArticleData>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
    ) {
        title?.let {
            Text(text = it, style = MaterialTheme.typography.titleMedium, color = Gray11)
        }
        contents.mapIndexed { index, article ->
            ArticleContent(title = "${index + 1}. ${article.title}", contents = article.contents)
        }
    }

}

@Composable
private fun ArticleContent(title: String, contents: List<DetailData>) {

    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleSmall, color = Gray11)
        contents.map { detail ->
            Text(
                text = "· ${detail.content}",
                style = MaterialTheme.typography.bodySmall,
                color = Gray11
            )
            DetailContent(contents = detail.detail)
        }
    }
}

@Composable
private fun DetailContent(contents: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.padding(start = 16.dp)
    ) {
        contents.map {
            Text(text = "· $it", style = MaterialTheme.typography.bodySmall, color = Gray11)
        }
    }

}

data class SectionData(
    val title: String, val contents: List<ArticleData>
)

data class ArticleData(
    val title: String, val contents: List<DetailData>
)

data class DetailData(
    val content: String, val detail: List<String> = emptyList()
)

@Preview(showBackground = true)
@Composable
private fun SectionPreview() {
    Dialog {}
}