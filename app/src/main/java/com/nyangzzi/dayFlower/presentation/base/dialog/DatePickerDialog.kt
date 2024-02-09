package com.nyangzzi.dayFlower.presentation.base.dialog

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nyangzzi.dayFlower.presentation.base.component.NumberPicker
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray8
import com.nyangzzi.dayFlower.ui.theme.White
import java.time.LocalDate


@Composable
fun DatePickerDialog(
    isShown: Boolean,
    year: Int,
    month: Int,
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {

    if (isShown) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            DateView(year = year, month = month, onConfirm = onConfirm, onDismiss = onDismiss)
        }
    }
}

@Composable
private fun DateView(year: Int, month: Int, onConfirm: (Int, Int) -> Unit, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        var selectedYear by remember { mutableStateOf(0) }
        var selectedMonth by remember { mutableStateOf(0) }

        LaunchedEffect(year, month) {
            selectedYear = year
            selectedMonth = month
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(30.dp)
            ) {
                NumberPicker(
                    sliderList = (1900..2099).toList(),
                    selectedData = year,
                    text = "년"
                ) {
                    selectedYear = it
                }

                NumberPicker(
                    sliderList = (LocalDate.MIN.monthValue..LocalDate.MAX.monthValue).toList(),
                    selectedData = month,
                    text = "월"
                ) {
                    selectedMonth = it
                }
            }

            Row(
                modifier = Modifier.height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Gray8,
                        containerColor = Gray1
                    )
                ) {
                    Text("취소", style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = { onConfirm(selectedYear, selectedMonth) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("확인", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DateViewPreview() {
    DateView(2024, 2, { _, _ -> }, {})
}




