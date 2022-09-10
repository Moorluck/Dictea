package com.example.dictea.api

import com.example.dictea.api.models.Result
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface LingueeApi {
    @GET("translations")
    fun getWord(@Query("query") query : String,
                @Query("src") sourceLanguage : String = "fr",
                @Query("dst") destinationLanguage : String = "en") : Call<List<Result>>
}