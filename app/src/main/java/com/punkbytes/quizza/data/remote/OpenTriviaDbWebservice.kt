package com.punkbytes.quizza.data.remote

import retrofit2.Call
import retrofit2.http.GET


interface OpenTriviaDbWebservice {
    @GET("api.php?amount=10&category=9&difficulty=medium&type=multiple")
    fun getTrivia(): Call<ApiTriviaListResult>

    @GET("api.php?amount=10&category=9&difficulty=medium&type=multiple")
    suspend fun getTriviaAsync(): ApiTriviaListResult
}
