package com.example.dictea.db.dao

import androidx.room.*
import com.example.dictea.models.Dictation
import com.example.dictea.models.DictationWord
import kotlinx.coroutines.flow.Flow

@Dao
interface DictationDAO {
    @Transaction
    @Query("SELECT * FROM Dictation")
    fun getDictations() : Flow<List<DictationWord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDictation(dictation : Dictation) : Long
}