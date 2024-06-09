package com.example.myapplication.ui.quiz.alphabet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityQuizAlphabetBinding
import com.example.myapplication.data.response.AlphabetQuestionsItem
import com.example.myapplication.data.response.AlphabetQuizResponse
import com.example.myapplication.factory.MainViewModelFactory
import com.example.myapplication.ui.main.MainViewModel
import com.example.myapplication.ui.number.DetailNumberViewModel

class QuizAlphabetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizAlphabetBinding
    private val quizViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(application)
    }

//    private val detailNumberViewModel by viewModels<DetailNumberViewModel> {
//        MainViewModelFactory.getInstance(application)
//    }

    private lateinit var adapter: QuizAlphabetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizViewModel.quizAlphabet.observe(this, Observer { quizAlphabetResponse ->
            quizAlphabetResponse?.let { response ->
                response.data?.questions?.let { questions ->
                    setupViewPager(questions.filterNotNull())
                }
            }
        })

        quizViewModel.isLoading.observe(this, Observer { isLoading ->
        })

        quizViewModel.getQuizAlphabet()

        binding.nextButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem + 1 < adapter.itemCount) {
                binding.viewPager.currentItem = currentItem + 1
            }
        }
    }

    private fun setupViewPager(questions: List<AlphabetQuestionsItem>) {
        adapter = QuizAlphabetAdapter(questions)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val progressText = "${position + 1}/${adapter.itemCount}"
                binding.progressTextView.text = progressText
            }
        })
    }
}
