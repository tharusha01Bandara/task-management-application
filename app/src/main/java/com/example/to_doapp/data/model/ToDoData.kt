package com.example.to_doapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.to_doapp.data.model.Priority
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey (autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String,

): Parcelable
