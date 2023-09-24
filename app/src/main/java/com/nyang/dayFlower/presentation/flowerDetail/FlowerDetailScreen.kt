package com.nyang.dayFlower.presentation.flowerDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FlowerDetailScreen(
    viewModel : FlowerDetailViewModel = hiltViewModel()
) {
}

@Composable
private fun FlowerDetailContent() {

}

@Preview
@Composable
private fun FlowerDetailPreview() {
    FlowerDetailContent()
}

