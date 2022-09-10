package com.example.dictea.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictea.api.LingueeApi
import com.example.dictea.api.RetrofitClient
import com.example.dictea.api.models.Result
import com.example.dictea.db.dao.DictationDAO
import com.example.dictea.db.dao.WordDAO
import com.example.dictea.mappers.ApiMapper
import com.example.dictea.models.DictationWord
import com.example.dictea.models.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordViewModel(private val wordDao: WordDAO, private val dictationDAO: DictationDAO) : ViewModel() {

    private val _words : MutableList<Word> = mutableListOf()
    private val words : MutableLiveData<List<Word>> = MutableLiveData()
    val Words : LiveData<List<Word>>
        get() = words

    private val _dictations : MutableList<DictationWord> = mutableListOf()
    private val dictations : MutableLiveData<List<DictationWord>> = MutableLiveData()
    val Dictations : LiveData<List<DictationWord>>
        get() = dictations

    private var api: LingueeApi = RetrofitClient.client.create(LingueeApi::class.java)

    fun testWord(word : String, cb : (w : Word?) -> Unit) {
        viewModelScope.launch {
            api.getWord(word)
                .enqueue(object : Callback<List<Result>> {
                    override fun onResponse(
                        call: Call<List<Result>>,
                        response: Response<List<Result>>
                    ) {
                        if (response.body() != null && response.body()!!.isNotEmpty()) {
                            val newWord = response.body()?.let { ApiMapper.toWord(it) }
                            if (newWord != null) {
                                cb(newWord)
                            }
                            else {
                                cb(null)
                            }
                        }
                        else {
                            cb(null)
                        }
                    }

                    override fun onFailure(call: Call<List<Result>>, t: Throwable) {
                        Log.d("WordVM", "Word not inserted !")
                        cb(null)
                    }

                })
        }
    }

    fun getWords() {
        viewModelScope.launch {
            wordDao.getWords().collect() {
                _words.clear()
                _words.addAll(it)
                words.value = _words
            }
        }
    }

    fun saveWord(word: Word) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordDao.insertWord(word)
                getWords()
            }
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordDao.deleteWord(word)
                getWords()
            }
        }
    }

    fun getDictation() {
        viewModelScope.launch {
            dictationDAO.getDictations().collect() {
                _dictations.clear()
                _dictations.addAll(it)
                dictations.value = _dictations
            }
        }
    }

    fun saveDictation() {
        viewModelScope.launch {

        }
    }

    fun deleteDictation() {
        viewModelScope.launch {

        }
    }
}