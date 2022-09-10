package com.example.dictea.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey val word: String,
    val audio: String,
    val dictationId : Int? = null,
    val order : Int? = null
)
