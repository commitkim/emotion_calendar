package com.example.emotioncalendar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
data class Memo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int = 0,
    val emotionId: Int,
    val date: String,
    val day: String,
    val content: String
)
