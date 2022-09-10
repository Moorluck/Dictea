package com.example.dictea.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Word(
    @PrimaryKey val word: String,
    val audio: String,
    var dictationId : Int? = null,
    var order : Int? = null
) : Serializable
