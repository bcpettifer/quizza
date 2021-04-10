package com.punkbytes.quizza.ui

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.punkbytes.quizza.data.TriviaData
import com.punkbytes.quizza.databinding.ViewQuizQuestionBinding


class QuizQuestionView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val binding: ViewQuizQuestionBinding
    private var data: TriviaData? = null
    private var listener: OnQuizQuestionComplete? = null

    init {
        binding = ViewQuizQuestionBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun bind(data: TriviaData) {
        this.data = data
        binding.textQuestion.text = decodeText(data.question)

        val answers = mutableListOf(
            data.correct_answer
        )
        answers.addAll(data.incorrect_answers)

        answers.shuffle()

        binding.buttonAnswerTopStart.text = decodeText(answers[0])
        binding.buttonAnswerTopEnd.text = decodeText(answers[1])
        binding.buttonAnswerBottomStart.text = decodeText(answers[2])
        binding.buttonAnswerBottomEnd.text = decodeText(answers[3])

        binding.buttonAnswerTopStart.setOnClickListener {
            checkAnswer((it as Button).text.toString())
        }
        binding.buttonAnswerTopEnd.setOnClickListener {
            checkAnswer((it as Button).text.toString())
        }
        binding.buttonAnswerBottomStart.setOnClickListener {
            checkAnswer((it as Button).text.toString())
        }
        binding.buttonAnswerBottomEnd.setOnClickListener {
            checkAnswer((it as Button).text.toString())
        }
    }

    fun bindListener(listener: OnQuizQuestionComplete) {
        this.listener = listener
    }

    private fun checkAnswer(selectedAnswer: String) {
        var isCorrect = false
        data?.correct_answer?.let {
            val answer = decodeText(it)
            if (answer == selectedAnswer) {
                isCorrect = true
            }
            setButtonResult(binding.buttonAnswerTopStart, answer)
            setButtonResult(binding.buttonAnswerTopEnd, answer)
            setButtonResult(binding.buttonAnswerBottomStart, answer)
            setButtonResult(binding.buttonAnswerBottomEnd, answer)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            reset()
            listener?.next(isCorrect)
        }, 1800)
    }

    private fun setButtonResult(button: Button, answer: String) {
        if (button.text == answer) {
            button.setBackgroundColor(Color.parseColor("#FF018786"))
        } else {
            button.setBackgroundColor(Color.parseColor("#FFBB2020"))
        }
    }

    private fun decodeText(text: String): String {
        val spanned = Html.fromHtml(text)
        val chars = CharArray(spanned.length)
        TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
        return String(chars)
    }

    private fun reset() {
        binding.buttonAnswerTopStart.text = ""
        binding.buttonAnswerTopStart.setBackgroundColor(Color.parseColor("#FF6200EE"))
        binding.buttonAnswerTopEnd.text = ""
        binding.buttonAnswerTopEnd.setBackgroundColor(Color.parseColor("#FF6200EE"))
        binding.buttonAnswerBottomStart.text = ""
        binding.buttonAnswerBottomStart.setBackgroundColor(Color.parseColor("#FF6200EE"))
        binding.buttonAnswerBottomEnd.text = ""
        binding.buttonAnswerBottomEnd.setBackgroundColor(Color.parseColor("#FF6200EE"))
    }
}

interface OnQuizQuestionComplete {
    fun next(didAnswerCorrectly: Boolean)
}
