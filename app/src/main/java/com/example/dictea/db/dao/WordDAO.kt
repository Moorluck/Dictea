package com.example.dictea.db.dao

import androidx.room.*
import com.example.dictea.models.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {
    @Query("SELECT * FROM Word ORDER BY word")
    fun getWords() : Flow<List<Word>>

    @Delete()
    fun deleteWord(word : Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word) : Long
}