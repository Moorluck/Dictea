package com.example.dictea.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity()
data class Dictation(
    @PrimaryKey val id: Int?,
    val name : String
) : Serializable
