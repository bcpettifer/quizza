package com.punkbytes.quizza.network

import com.punkbytes.quizza.data.TriviaListResult
import retrofit2.Call
import retrofit2.http.GET


interface OpenTriviaDatabase {
    @GET("api.php?amount=10&category=9&difficulty=medium&type=multiple")
    fun getTrivia(): Call<TriviaListResult>
}
