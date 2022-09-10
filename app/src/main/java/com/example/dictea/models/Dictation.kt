package com.example.dictea.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Dictation(
    @PrimaryKey val id: Int,
    val name : String
)
