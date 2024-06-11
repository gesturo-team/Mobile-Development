package com.example.myapplication.ui.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNumber.setOnClickListener {
            startActivity(Intent(this@QuizActivity, QuizDetailActivity::class.java).putExtra("TYPE", "number"))
        }
        binding.btnAlphabet.setOnClickListener {
            startActivity(Intent(this@QuizActivity, QuizDetailActivity::class.java).putExtra("TYPE", "alphabet"))
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