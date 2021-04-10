package com.punkbytes.quizza.data

import androidx.annotation.Keep

@Keep
data class TriviaData(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        val correct_answer: String,
        val incorrect_answers: List<String>
)

@Keep
data class TriviaListResult(
        val response_code: Int,
        val results: List<TriviaData>
)
