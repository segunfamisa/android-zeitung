package com.segunfamisa.zeitung.data.common

fun String.nullify(): String? {
    return if (this.isEmpty()) {
        null
    } else {
        this
    }
}
