package com.punkbytes.quizza.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.punkbytes.quizza.data.QuizData
import com.punkbytes.quizza.databinding.ViewResultsBinding

class ResultsView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val binding = ViewResultsBinding.inflate(LayoutInflater.from(context), this, true)

    fun bind(data: QuizData, listener: OnRestartClick) {
        binding.textScore.text = "${data.correctAnswersAmount}/${data.questionAmount}"

        binding.buttonReplay.setOnClickListener {
            listener.restart()
        }
    }
}

interface OnRestartClick {
    fun restart()
}
