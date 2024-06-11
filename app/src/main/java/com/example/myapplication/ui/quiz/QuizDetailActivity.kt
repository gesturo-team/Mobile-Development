package com.example.myapplication.ui.quiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.data.response.QuizData
import com.example.myapplication.data.response.QuizQuestionsItem
import com.example.myapplication.databinding.ActivityQuizDetailBinding
import com.example.myapplication.factory.MainViewModelFactory
import com.example.myapplication.ui.main.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuizDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizDetailBinding
    private val quizViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(application)
    }

    private lateinit var adapter: QuizDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizViewModel.quizResponse.observe(this, Observer { quizAlphabetResponse ->
            quizAlphabetResponse?.let { response ->
                response.data?.questions?.let { questions ->
                    setupViewPager(questions.filterNotNull())
                }
            }
        })

        quizViewModel.isLoading.observe(this, Observer { isLoading ->
        })

        val type = intent.getStringExtra("TYPE")
        if(type == "number") {
            quizViewModel.getQuizNumber()
        } else if (type == "alphabet") {
            quizViewModel.getQuizAlphabet()
        }

        binding.nextButton.setOnClickListener {
            if (type == "alphabet") {
                submitAlphabet()
            } else if (type == "number") {
                submitNumber()
            }
        }

        quizViewModel.submit.observe(this, Observer { response ->
            if (response != null && response.success == true) {
                Toast.makeText(this, "Answers submitted successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to submit answers!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupViewPager(questions: List<QuizQuestionsItem>) {
        adapter = QuizDetailAdapter(questions)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val progressText = "${position + 1}/${adapter.itemCount}"
                binding.progressTextView.text = progressText
            }
        })
    }

    private fun collectAnswersFromAdapter(): List<QuizQuestionsItem> {
        return (binding.viewPager.adapter as QuizDetailAdapter).questionsItem
    }

    private fun calculateScore(answers: List<QuizQuestionsItem>): Int {
        return answers.count { it.userAnswer == it.answers?.find { correct -> correct?.correct == true }?.value }
    }

    private fun submitAlphabet() {
        val answers = collectAnswersFromAdapter()
        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val request = QuizData(
            score = calculateScore(answers).toString(),
            type = "alphabet",
            createdAt = currentDate,
            updatedAt = currentDate,
            questions = answers
        )
        quizViewModel.submitAnswers(request)
        Toast.makeText(this, calculateScore(answers).toString(), Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun submitNumber() {
        val answers = collectAnswersFromAdapter()
        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val request = QuizData(
            score = calculateScore(answers).toString(),
            type = "number",
            createdAt = currentDate,
            updatedAt = currentDate,
            questions = answers
        )
        quizViewModel.submitAnswers(request)
        Toast.makeText(this, calculateScore(answers).toString(), Toast.LENGTH_SHORT).show()
        finish()
    }

//    private fun showScoreDialog(answers: List<AlphabetQuestionsItem>) {
//        val userScore = calculateScore(answers = answers)
//        AlertDialog.Builder(this).apply {
//            setTitle("FINISHED")
//        }
//    }
}


