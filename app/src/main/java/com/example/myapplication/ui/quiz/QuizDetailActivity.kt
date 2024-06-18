package com.example.myapplication.ui.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.data.response.QuizData
import com.example.myapplication.data.response.QuizQuestionsItem
import com.example.myapplication.databinding.ActivityQuizDetailBinding
import com.example.myapplication.databinding.ScoreDialogBinding
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
    private lateinit var answers: List<QuizQuestionsItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.navQuiz)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.navQuiz.setNavigationOnClickListener {
            onBackPressed()
        }

        answers = listOf()

        quizViewModel.quizResponse.observe(this) { quizAlphabetResponse ->
            quizAlphabetResponse?.let { response ->
                response.data?.questions?.let { questions ->
                    setupViewPager(questions.filterNotNull())
                }
            }
        }

        quizViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressQuiz.visibility = View.VISIBLE
            } else {
                binding.progressQuiz.visibility = View.GONE
            }
        }

        val type = intent.getStringExtra("TYPE")
        if (type == "number") {
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

        quizViewModel.submit.observe(this) { response ->
            if (response != null && response.success == true) {
                Toast.makeText(this, "Congrats, you finished it", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "It's failed!", Toast.LENGTH_SHORT).show()
            }
        }
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

        answers = userAnswers()
    }

    private fun userAnswers(): List<QuizQuestionsItem> {
        return (binding.viewPager.adapter as QuizDetailAdapter).questionsItem
    }

    private fun userScore(answers: List<QuizQuestionsItem>): Int {
        return answers.count { it.userAnswer == it.answers?.find { correct -> correct?.correct == true }?.value }
    }

    private fun submitAlphabet() {
//        val answers = collectAnswersFromAdapter()
        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val sendData = QuizData(
            score = userScore(answers).toString(),
            type = "alphabet",
            createdAt = currentDate,
            updatedAt = currentDate,
            questions = answers
        )
        quizViewModel.submitAnswers(sendData)

        scoreDialog()
    }

    private fun submitNumber() {
        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val sendData = QuizData(
            score = userScore(answers).toString(),
            type = "number",
            createdAt = currentDate,
            updatedAt = currentDate,
            questions = answers
        )
        quizViewModel.submitAnswers(sendData)
        scoreDialog()
    }

    @SuppressLint("SetTextI18n")
    private fun scoreDialog() {
        val scoreBinding = ScoreDialogBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
            .setView(scoreBinding.root)
        val dialog = builder.create()
        scoreBinding.tvScore.text = "SCORE : ${userScore(answers)}"
        scoreBinding.btnOk.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

}


