package com.example.dictea.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictea.db.dao.DictationDAO
import com.example.dictea.db.dao.WordDAO
import com.example.dictea.models.Dictation
import com.example.dictea.models.Word

@Database(entities = [Word::class, Dictation::class], version = 6, exportSchema = false)
abstract class DbHelper : RoomDatabase() {
    abstract fun words() : WordDAO
    abstract fun dictations() : DictationDAO

    companion object {
        const val DB_NAME = "dictea_db"
        private var instance : DbHelper? = null
        fun instance(context: Context) : DbHelper {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(context, DbHelper::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}