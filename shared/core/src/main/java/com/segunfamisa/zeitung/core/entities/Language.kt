package com.segunfamisa.zeitung.core.entities

import java.util.Locale

private val supportedLanguages = listOf(
    "ar",
    "de",
    "en",
    "es",
    "fr",
    "he",
    "it",
    "nl",
    "no",
    "pt",
    "ru",
    "sv",
    "ud",
    "zh"
)

fun defaultLanguage(): String {
    val language = Locale.getDefault().displayLanguage.lowercase()
    return if (language in supportedLanguages) {
        language
    } else {
        "en"
    }
}
