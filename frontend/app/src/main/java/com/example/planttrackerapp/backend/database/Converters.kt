package com.example.planttrackerapp.backend.database;

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.room.TypeConverter
import java.util.Calendar
import com.example.planttrackerapp.model.Species


class Converters{

    private val gson = Gson()

    @TypeConverter
    fun fromSpecies(species: Species?): String? {
        return species?.let { gson.toJson(it) }
    }

    // Konwertuje JSON do obiektu Species
    @TypeConverter
    fun toSpecies(data: String?): Species? {
        return data?.let {
            gson.fromJson(it, Species::class.java)
        }
    }

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
