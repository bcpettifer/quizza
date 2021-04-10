package com.punkbytes.quizza.data.repository

import android.text.TextUtils
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.punkbytes.quizza.data.model.QuizQuestion
import com.punkbytes.quizza.data.remote.ApiTriviaListResult
import com.punkbytes.quizza.data.remote.OpenTriviaDbWebservice
import com.punkbytes.quizza.data.remote.ServiceBuilder

class QuizRepository {
    private val webservice: OpenTriviaDbWebservice = ServiceBuilder.buildService(
        OpenTriviaDbWebservice::class.java
    )
    private val cache = QuizCache()

    suspend fun getQuestionAsync(questionNumber: Int): QuizQuestion {
        val cached = cache.getQuestion(questionNumber)
        if (cached != null) {
            return cached
        }
        val quizApiResult = webservice.getTriviaAsync()
        val questions = mapApi(quizApiResult)
        cache.putQuestionSet(questions)
        return questions[questionNumber]
    }

    fun getQuestion(questionNumber: Int): QuizQuestion {
        val cached = cache.getQuestion(questionNumber)
        if (cached != null) {
            return cached
        }
        val t = Thread {
            val quizApiResult = webservice.getTrivia().execute().body()
            val questions = mapApi(quizApiResult!!)
            cache.putQuestionSet(questions)
        }
        t.start()
        t.join()

        return cache.getQuestion(questionNumber)!!
    }

    private fun mapApi(apiTriviaResult: ApiTriviaListResult): List<QuizQuestion> {
        return apiTriviaResult.results.map {
            QuizQuestion(
                question = it.question.decodeHtml(),
                correctAnswer = it.correct_answer.decodeHtml(),
                incorrectAnswers = it.incorrect_answers.map { answer -> answer.decodeHtml() }
            )
        }
    }

    private fun String.decodeHtml(): String {
        val spanned = HtmlCompat.fromHtml(this, FROM_HTML_MODE_COMPACT)
        val chars = CharArray(spanned.length)
        TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
        return String(chars)
    }
}
