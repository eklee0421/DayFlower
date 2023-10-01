package com.nyang.dayFlower.presentation.base.component

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate

@Composable
fun BaseDatePicker(isShown : Boolean, nowDate: LocalDate, selectDate: (LocalDate)->Unit){

    if(!isShown) return

    DatePickerDialog(
        LocalContext.current,
        { _, year, month, day ->
            selectDate(LocalDate.of(year, month+1, day))
        },
        nowDate.year,
        nowDate.month.value-1,
        nowDate.dayOfMonth,
    ).show()
}