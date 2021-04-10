package com.punkbytes.quizza.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ApiTriviaData(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        val correct_answer: String,
        val incorrect_answers: List<String>
)

@Keep
data class ApiTriviaListResult(
    @field:Json(name = "response_code") val response_code: Int,
    @field:Json(name = "results") val results: List<ApiTriviaData>
)
