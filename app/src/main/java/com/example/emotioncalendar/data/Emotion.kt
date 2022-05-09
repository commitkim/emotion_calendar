package com.example.emotioncalendar.data

import androidx.room.Entity
import java.io.Serializable

@Entity
data class Emotion(
    val drawable: Int,
) : Serializable
