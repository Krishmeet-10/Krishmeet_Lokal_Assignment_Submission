package com.example.lokal_assignment_app.Database_files

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StringEntryDao {
    @Insert
    suspend fun insert(entry: StringEntry)

    @Query("SELECT * FROM string_entries")
    suspend fun getAllEntries(): List<StringEntry>

    @Query("SELECT * FROM string_entries WHERE value = :value LIMIT 1")
    suspend fun getEntryByValue(value: String): StringEntry?

    @Delete
    suspend fun delete(entry: StringEntry)

    @Query("DELETE FROM string_entries WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}
