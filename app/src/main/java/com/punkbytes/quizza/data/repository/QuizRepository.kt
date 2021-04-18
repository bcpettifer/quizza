package com.punkbytes.quizza.data.repository

import com.punkbytes.quizza.data.model.QuizQuestion

interface QuizRepository {
    suspend fun getQuestionAsync(questionNumber: Int): QuizQuestion
}
