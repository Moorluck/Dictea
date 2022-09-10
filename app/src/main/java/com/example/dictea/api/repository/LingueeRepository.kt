package com.example.dictea.api.repository

import android.util.Log
import com.example.dictea.api.LingueeApi
import com.example.dictea.api.models.Result
import com.example.dictea.mappers.ApiMapper
import com.example.dictea.models.Word
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LingueeRepository {
    companion object {
        fun testWord(word : String, api : LingueeApi, cb : (w : Word?) -> Unit) {
            api.getWord(word)
                .enqueue(object : Callback<List<Result>> {
                    override fun onResponse(
                        call: Call<List<Result>>,
                        response: Response<List<Result>>
                    ) {
                        Log.d("API", response.toString())
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
                        t.message?.let { Log.d("API", it) }
                        cb(null)
                    }

                })
        }
    }
}