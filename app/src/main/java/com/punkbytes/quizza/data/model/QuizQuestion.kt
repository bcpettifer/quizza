package com.punkbytes.quizza.data.model

data class QuizQuestion(
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)
