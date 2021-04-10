package com.punkbytes.quizza.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.punkbytes.quizza.data.model.QuizData
import com.punkbytes.quizza.data.model.QuizQuestionViewModel
import com.punkbytes.quizza.data.repository.QuizRepository
import com.punkbytes.quizza.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiseQuiz()
    }

    private fun initialiseQuiz() {
        val questionCount = 10 // Hardcoded for now
        val repository = QuizRepository()
        var triviaIndex = 0
        var correctAnswerCount = 0
        val viewModel = QuizQuestionViewModel(triviaIndex, repository)

        binding.quiz.bind(viewModel, this)

        binding.quiz.bindListener(object : OnQuizQuestionComplete {
            override fun next(didAnswerCorrectly: Boolean) {
                triviaIndex++
                if (didAnswerCorrectly) correctAnswerCount++
                if (triviaIndex < questionCount) {
                    binding.quiz.bind(QuizQuestionViewModel(triviaIndex, repository), this@MainActivity)
                } else {
                    binding.quiz.visibility = View.GONE
                    binding.results.visibility = View.VISIBLE

                    binding.results.bind(QuizData(questionCount, correctAnswerCount), object : OnRestartClick {
                        override fun restart() {
                            triviaIndex = 0
                            binding.quiz.bind(viewModel, this@MainActivity)
                            binding.quiz.visibility = View.VISIBLE
                            binding.results.visibility = View.GONE
                        }

                    })
                    Toast.makeText(this@MainActivity, "YOU WIN!", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
