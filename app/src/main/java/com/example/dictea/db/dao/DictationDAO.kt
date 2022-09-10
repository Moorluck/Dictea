package com.example.dictea.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.dictea.models.DictationWord
import kotlinx.coroutines.flow.Flow

@Dao
interface DictationDAO {
    @Transaction
    @Query("SELECT * FROM Dictation")
    fun getDictations() : Flow<List<DictationWord>>
}