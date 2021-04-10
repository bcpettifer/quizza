package com.punkbytes.quizza.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.punkbytes.quizza.data.repository.QuizRepository

class QuizQuestionViewModel(
    questionNumber: Int,
    repository: QuizRepository
) : ViewModel() {
    private val index: Int = questionNumber

    private val _question = MutableLiveData<QuizQuestion>()
    val question: LiveData<QuizQuestion> = _question

    init {
        _question.value = repository.getQuestion(index)
    }
}
