package com.example.dictea.models

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class DictationWord (
    @Embedded
    val dictation: Dictation,

    @Relation(
        parentColumn = "id",
        entityColumn = "dictationId"
    )
    val words: List<Word>
) : Serializable
