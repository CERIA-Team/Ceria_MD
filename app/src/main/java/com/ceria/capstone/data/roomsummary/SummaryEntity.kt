package com.ceria.capstone.data.roomsummary

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SummaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sessionId: String,
    val artists: String,
    val albumNames: String,
    val imageUrls: String
)

