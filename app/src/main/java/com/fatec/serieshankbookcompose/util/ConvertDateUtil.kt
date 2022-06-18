package com.fatec.serieshankbookcompose.util

import java.text.SimpleDateFormat
import java.util.*

sealed class ConvertDateUtil {
    companion object Factory {
        fun Date.formatTo(
            dateFormat: String,
            timeZone: TimeZone = TimeZone.getTimeZone("UTC")
        ): String {
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
            formatter.timeZone = timeZone
            return formatter.format(this)
        }
    }
}