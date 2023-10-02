package com.nyang.dayFlower.presentation.feature.todayFlowerWidget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyang.dayFlower.presentation.feature.mainFlower.MainFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayFlowerWidgetReceiver : GlanceAppWidgetReceiver() {

    // Let MyAppWidgetReceiver know which GlanceAppWidget to use
    override val glanceAppWidget: GlanceAppWidget = TodayFlowerWidget()
}