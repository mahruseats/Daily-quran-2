package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val surahNumber: Int,
    val surahNameEnglish: String,
    val surahNameBangla: String,
    val ayahNumber: Int,
    val textArabic: String,
    val translationEnglish: String,
    val translationBangla: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "last_read")
data class LastReadEntity(
    @PrimaryKey
    val id: Int = 1, // Single entry
    val surahNumber: Int,
    val surahNameEnglish: String,
    val surahNameBangla: String,
    val ayahNumber: Int,
    val timestamp: Long = System.currentTimeMillis()
)
