package com.example.androidprojectdn2021.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd")
    return format.format(date)
}