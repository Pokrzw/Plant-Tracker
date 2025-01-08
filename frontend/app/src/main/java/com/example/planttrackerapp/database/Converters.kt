package com.example.planttrackerapp.database;

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateTime: String?): LocalDateTime? {
        return dateTime?.let { LocalDateTime.parse(it, formatter) }
    }

    private val gson = Gson()

    // Konwersja List<Calendar> na String (JSON)
    @TypeConverter
    fun fromCalendarList(calendarList: List<Calendar>?): String? {
        return calendarList?.map { it.timeInMillis }?.let { gson.toJson(it) }
    }

    // Konwersja String (JSON) na List<Calendar>
    @TypeConverter
    fun toCalendarList(data: String?): List<Calendar>? {
        return data?.let {
            val type = object : TypeToken<List<Long>>() {}.type
            val timestamps: List<Long> = gson.fromJson(it, type)
            timestamps.map { timestamp ->
                Calendar.getInstance().apply { timeInMillis = timestamp }
            }
        }
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun toCalendar(timestamp: Long?): Calendar? {
        return timestamp?.let {
            Calendar.getInstance().apply {
                timeInMillis = it
            }
        }
    }
}
