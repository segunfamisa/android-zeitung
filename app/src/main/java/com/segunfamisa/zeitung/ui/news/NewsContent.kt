package com.segunfamisa.zeitung.ui.news

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun NewsContent(
    newsViewModel: NewsViewModel
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Hello news")
    }
}

@Composable
@Preview
private fun NewsContentPreview() {
    Column {
        Text(text = "Hello news")
    }
}