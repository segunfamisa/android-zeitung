package com.segunfamisa.zeitung.common.theme.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    group = "themed ui preview",
    showBackground = true,
    name = "light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    group = "themed ui preview",
    showBackground = true,
    name = "dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ThemedUiPreview