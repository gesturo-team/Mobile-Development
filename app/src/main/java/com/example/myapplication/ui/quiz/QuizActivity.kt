package com.example.myapplication.ui.quiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDetailNumberBinding
import com.example.myapplication.databinding.ActivityQuizBinding
import com.example.myapplication.factory.MainViewModelFactory
import com.example.myapplication.ui.number.DetailNumberViewModel
import com.example.myapplication.ui.quiz.alphabet.QuizAlphabetActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNumber.setOnClickListener {
            startActivity(Intent(this@QuizActivity, QuizAlphabetActivity::class.java))
        }
        binding.btnAlphabet.setOnClickListener {
            startActivity(Intent(this@QuizActivity, QuizAlphabetActivity::class.java))
        }
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_quiz)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}