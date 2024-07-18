package com.example.lokal_assignment_app.Database_files

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "string_entries")
data class StringEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: String //json string
)
