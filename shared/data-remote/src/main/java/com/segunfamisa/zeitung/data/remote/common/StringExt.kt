package com.segunfamisa.zeitung.data.remote.common

fun String.nullify(): String? {
    return if (this.isEmpty()) {
        null
    } else {
        this
    }
}
