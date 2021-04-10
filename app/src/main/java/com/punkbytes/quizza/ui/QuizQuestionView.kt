package com.punkbytes.quizza.ui

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import com.punkbytes.quizza.data.model.QuizQuestion
import com.punkbytes.quizza.data.model.QuizQuestionViewModel
import com.punkbytes.quizza.databinding.ViewQuizQuestionBinding


class QuizQuestionView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val binding: ViewQuizQuestionBinding
    private var listener: OnQuizQuestionComplete? = null

    init {
        binding = ViewQuizQuestionBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun bind(data: QuizQuestionViewModel, lifecycleOwner: LifecycleOwner) {
        data.question.observe(lifecycleOwner) {
            updateUi(it)
        }
    }

    fun bindListener(listener: OnQuizQuestionComplete) {
        this.listener = listener
    }

    private fun updateUi(model: QuizQuestion) {
        binding.textQuestion.text = model.question

        val answers = mutableListOf(
            model.correctAnswer
        )
        answers.addAll(model.incorrectAnswers)

        answers.shuffle()

        binding.buttonAnswerTopStart.text = answers[0]
        binding.buttonAnswerTopEnd.text = answers[1]
        binding.buttonAnswerBottomStart.text = answers[2]
        binding.buttonAnswerBottomEnd.text = answers[3]

        binding.buttonAnswerTopStart.setOnClickListener {
            checkAnswer((it as Button).text.toString(), model.correctAnswer)
        }
        binding.buttonAnswerTopEnd.setOnClickListener {
            checkAnswer((it as Button).text.toString(), model.correctAnswer)
        }
        binding.buttonAnswerBottomStart.setOnClickListener {
            checkAnswer((it as Button).text.toString(), model.correctAnswer)
        }
        binding.buttonAnswerBottomEnd.setOnClickListener {
            checkAnswer((it as Button).text.toString(), model.correctAnswer)
        }
    }

    private fun checkAnswer(selectedAnswer: String, correctAnswer: String) {
        val isCorrect = selectedAnswer == correctAnswer
        setButtonResult(binding.buttonAnswerTopStart, correctAnswer)
        setButtonResult(binding.buttonAnswerTopEnd, correctAnswer)
        setButtonResult(binding.buttonAnswerBottomStart, correctAnswer)
        setButtonResult(binding.buttonAnswerBottomEnd, correctAnswer)

        Handler(Looper.getMainLooper()).postDelayed({
            reset()
            listener?.next(isCorrect)
        }, 1500)
    }

    private fun setButtonResult(button: Button, answer: String) {
        if (button.text == answer) {
            button.setBackgroundColor(Color.parseColor("#FF018786"))
        } else {
            button.setBackgroundColor(Color.parseColor("#FFBB2020"))
        }
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
