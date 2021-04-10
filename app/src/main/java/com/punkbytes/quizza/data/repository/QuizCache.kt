package com.punkbytes.quizza.data.repository

import com.punkbytes.quizza.data.model.QuizQuestion

class QuizCache {
    private var questions: List<QuizQuestion> = emptyList()

    fun putQuestionSet(questions: List<QuizQuestion>) {
        this.questions = questions
    }

    fun getQuestion(number: Int): QuizQuestion? {
        return questions.getOrNull(number)
    }
}
