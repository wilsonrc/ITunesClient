package com.example.itunesclient.ui

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toDateTime(): String? {
    return try {
        val isoFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val zonedDateTime = ZonedDateTime.parse(this, isoFormatter)

        val localDateTime = LocalDateTime.from(zonedDateTime)
        val legibleFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDateTime.format(legibleFormatter)
    } catch (e: Exception) {
        null
    }
}