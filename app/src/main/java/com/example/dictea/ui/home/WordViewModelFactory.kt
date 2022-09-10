package com.example.dictea.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictea.db.DbHelper

class WordViewModelFactory(val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DbHelper.instance(context)
        return WordViewModel(db.words(), db.dictations()) as T
    }
}