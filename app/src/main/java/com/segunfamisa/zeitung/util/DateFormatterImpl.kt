package com.segunfamisa.zeitung.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateFormatterImpl @Inject constructor(): DateFormatter {

    private val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    override fun format(date: Date): String = sdf.format(date)
}
