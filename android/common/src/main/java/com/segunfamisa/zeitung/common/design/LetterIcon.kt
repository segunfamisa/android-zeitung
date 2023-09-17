package com.segunfamisa.zeitung.common.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.common.theme.typography

@Composable
fun LetterIcon(
    letters: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorScheme().primaryContainer,
    textColor: Color = contentColorFor(backgroundColor = backgroundColor)
) {
    val startingStyle = typography().displayLarge.copy(color = textColor)
    var textStyle by remember { mutableStateOf(startingStyle) }
    var readyToDraw by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.background(
            color = backgroundColor,
            shape = AbsoluteRoundedCornerShape(percent = 20)
        )
    ) {
        Text(
            text = letters,
            modifier = Modifier
                .padding(4.dp)
                .align(alignment = Alignment.Center)
                .drawWithContent {
                    if (readyToDraw) drawContent()
                },
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.didOverflowHeight) {
                    textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
                } else {
                    readyToDraw = true
                }
            }
        )
    }
}

@Preview
@Composable
private fun PreviewLetterIcon() {
    ZeitungTheme {
        LetterIcon(
            letters = "AB",
            modifier = Modifier.size(56.dp),
        )
    }
}
