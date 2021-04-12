package com.punkbytes.quizza.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.punkbytes.quizza.data.model.QuizQuestionViewModel
import com.punkbytes.quizza.databinding.FragmentQuizQuestionBinding

class QuizQuestionFragment: Fragment() {

    private var _binding: FragmentQuizQuestionBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun bind(model: QuizQuestionViewModel) {
        binding.question.bind(model, this)
    }
}
