package com.punkbytes.quizza.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.punkbytes.quizza.data.QuizData
import com.punkbytes.quizza.data.TriviaListResult
import com.punkbytes.quizza.databinding.ActivityMainBinding
import com.punkbytes.quizza.network.OpenTriviaDatabase
import com.punkbytes.quizza.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiseQuiz()
    }

    private fun initialiseQuiz() {
        val triviaApi = ServiceBuilder.buildService(OpenTriviaDatabase::class.java)

        triviaApi.getTrivia().enqueue(object : retrofit2.Callback<TriviaListResult> {
            override fun onResponse(call: Call<TriviaListResult>, response: Response<TriviaListResult>) {
                Toast.makeText(this@MainActivity, "onResponse", Toast.LENGTH_LONG).show()
                Log.d("TODO", response.toString())

                var triviaIndex = 0
                var correctAnswerCount = 0

                binding.quiz.bind(response.body()!!.results[triviaIndex])

                binding.quiz.bindListener(object : OnQuizQuestionComplete {
                    override fun next(didAnswerCorrectly: Boolean) {
                        triviaIndex++
                        if (didAnswerCorrectly) correctAnswerCount++
                        if (triviaIndex < response.body()!!.results.size) {
                            binding.quiz.bind(response.body()!!.results[triviaIndex])
                        } else {
                            binding.quiz.visibility = View.GONE
                            binding.results.visibility = View.VISIBLE

                            binding.results.bind(QuizData(response.body()!!.results.size, correctAnswerCount), object : OnRestartClick {
                                override fun restart() {
                                    triviaIndex = 0
                                    binding.quiz.bind(response.body()!!.results[triviaIndex])
                                    binding.quiz.visibility = View.VISIBLE
                                    binding.results.visibility = View.GONE
                                }

                            })
                            Toast.makeText(this@MainActivity, "YOU WIN!", Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }

            override fun onFailure(call: Call<TriviaListResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "onFailure: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }

        })
    }
}
