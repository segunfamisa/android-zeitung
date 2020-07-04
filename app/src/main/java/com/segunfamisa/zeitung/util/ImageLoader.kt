package com.segunfamisa.zeitung.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.*
import androidx.ui.core.ContentScale
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Canvas
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Image
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.asImageAsset
import androidx.ui.graphics.drawscope.drawCanvas
import androidx.ui.layout.size
import androidx.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Composes a glide image.
 *
 * Adapted from https://github.com/vinaygaba/Learn-Jetpack-Compose-By-Example/blob/master/app/src/main/java/com/example/jetpackcompose/image/ImageActivity.kt#L219
 */
@Composable
fun GlideImage(
    url: String,
    @DrawableRes placeHolderResId: Int? = null,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    var image by state<ImageAsset?> { null }
    var drawable by state<Drawable?> { null }
    var failure by state { false }
    val context = ContextAmbient.current
    onCommit(url) {
        val glide = Glide.with(context)

        val target = object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                image = null
                drawable = placeholder
                failure = true
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                image = resource.asImageAsset()
            }
        }

        val requestBuilder = glide.asBitmap()
            .load(url)

        placeHolderResId?.let {
            requestBuilder.placeholder(placeHolderResId)
        }
        requestBuilder.into(target)

        onDispose {
            image = null
            drawable = null
            glide.clear(target)
        }
    }

    val finalImage = image
    val placeHolderDrawable = drawable

    if (finalImage != null) {
        Box(modifier = modifier, gravity = ContentGravity.Center) {
            Image(asset = finalImage, modifier = modifier, contentScale = contentScale)
        }
    } else if (placeHolderDrawable != null) {
        Canvas(modifier = modifier) {
            drawCanvas { canvas, _ ->
                placeHolderDrawable.draw(canvas.nativeCanvas)
            }
        }
    } else if (failure) {
        Box(modifier = Modifier.size(0.dp), gravity = ContentGravity.Center) {

        }
    }
}
