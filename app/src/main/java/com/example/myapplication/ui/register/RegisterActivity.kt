package com.example.myapplication.ui.register

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textSpan = "Already have an account? Log In Now!"
        val stringSpan = SpannableString(textSpan)

        val black = ForegroundColorSpan(Color.BLACK)
        stringSpan.setSpan(black, 0, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val blue = ForegroundColorSpan(Color.parseColor("#001E6D"))
        stringSpan.setSpan(blue, 25, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#001E6D")
                ds.isUnderlineText = false
            }
        }

        stringSpan.setSpan(clickableSpan, 25, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val bold = StyleSpan(Typeface.BOLD)
        stringSpan.setSpan(bold, 25, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvRegister.text = stringSpan
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }
}