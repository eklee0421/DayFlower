package com.nyang.dayFlower.presentation.feature.todayFlowerWidget
/*

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.nyang.dayFlower.MainActivity
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.ui.theme.DayFlowerTheme

class TodayFlowerWidget: GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            //BIG_SQUARE
        )
    )
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            // create your AppWidget here
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {

        val size = LocalSize.current
        DayFlowerTheme {
            Column(modifier = GlanceModifier.fillMaxSize().clickable(actionStartActivity<MainActivity>())) {

                when(size){
                    SMALL_SQUARE -> SmallSquareContent()
                    HORIZONTAL_RECTANGLE -> HorizontalRectangleContent()

                }

            }
        }
    }

    @Composable
    private fun SmallSquareContent(){
        Text("small")
    }

    @Composable
    private fun HorizontalRectangleContent(){
        Text("middle")
    }


}*/