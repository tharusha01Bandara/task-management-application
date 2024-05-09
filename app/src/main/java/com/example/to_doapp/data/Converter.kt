package com.example.to_doapp.data

import androidx.room.TypeConverter
import com.example.to_doapp.data.model.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority):String{
        return priority.name
    }

    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}