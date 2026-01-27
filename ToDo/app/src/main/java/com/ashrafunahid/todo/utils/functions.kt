package com.ashrafunahid.todo.utils

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDateTime(timeStamp: Long, format: String): String? =
    SimpleDateFormat(format, Locale.getDefault()).format(Date(timeStamp))