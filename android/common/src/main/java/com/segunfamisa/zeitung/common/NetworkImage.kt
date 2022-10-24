package com.segunfamisa.zeitung.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.segunfamisa.zeitung.common.theme.compositedOnSurface

/**
 * A wrapper around [CoilImage] setting a default [contentScale] and loading placeholder.
 *
 * Source: https://github.com/android/compose-samples/blob/34a75fb3672622a3fb0e6a78adc88bbc2886c28f/Owl/app/src/main/java/com/example/owl/ui/utils/NetworkImage.kt
 */
@ExperimentalCoilApi
@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color? = MaterialTheme.colors.compositedOnSurface(0.2f)
) {
    Box(modifier = modifier) {
        val painter = rememberImagePainter(data = url)

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = contentScale,
        )

        if (painter.state is AsyncImagePainter.State.Loading && placeholderColor != null) {
            Spacer(modifier = Modifier
                .matchParentSize()
                .background(placeholderColor))
        }
    }

}
