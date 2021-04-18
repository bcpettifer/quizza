package com.punkbytes.quizza.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.punkbytes.quizza.data.repository.QuizRepository
import kotlinx.coroutines.launch

class QuizQuestionViewModel(
    questionNumber: Int,
    repository: QuizRepository
) : ViewModel() {
    private val index: Int = questionNumber

    private val _question = MutableLiveData<QuizQuestion>()
    val question: LiveData<QuizQuestion> = _question

    init {
        viewModelScope.launch {
            _question.value = repository.getQuestionAsync(index)
        }
    }
}
