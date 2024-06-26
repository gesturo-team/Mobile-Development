package com.example.myapplication.ui.learn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentLearnBinding
import com.example.myapplication.ui.alphabet.AlphabetActivity
import com.example.myapplication.ui.number.NumberActivity
import com.example.myapplication.ui.quiz.QuizActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LearnFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rlQuiz.setOnClickListener {
            val quizIntent = Intent(activity, QuizActivity::class.java)
            activity?.startActivity(quizIntent)
        }
        binding.rlNumbers.setOnClickListener {
            val numberIntent = Intent(activity, NumberActivity::class.java)
            activity?.startActivity(numberIntent)
        }
        binding.rlAlphabet.setOnClickListener {
            val alphabetIntent = Intent(activity, AlphabetActivity::class.java)
            activity?.startActivity(alphabetIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
