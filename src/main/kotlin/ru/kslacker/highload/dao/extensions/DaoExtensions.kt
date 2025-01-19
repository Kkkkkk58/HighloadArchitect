package ru.kslacker.highload.dao.extensions

import java.sql.ResultSet
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun ResultSet.getOffsetDateTime(columnLabel: String): OffsetDateTime {
    return getTimestamp(columnLabel).toInstant().atOffset(ZoneOffset.UTC)
}

fun ResultSet.getLocalDate(columnLabel: String): LocalDate {
    return getDate(columnLabel).toLocalDate()
}