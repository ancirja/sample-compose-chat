package com.sample.chat.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateString(): String =
    Date(this).toDateString()

fun Date.toDateString(): String {
    val dateFormatter = SimpleDateFormat("EEEE, hh:mm", Locale.getDefault())
    return dateFormatter.format(this)
}
