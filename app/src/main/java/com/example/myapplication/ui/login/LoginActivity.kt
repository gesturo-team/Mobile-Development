package com.example.myapplication.ui.login

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.factory.AuthViewModelFactory
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        viewModel.isError.observe(this) { isError ->
            Toast.makeText(this, isError, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) { loading ->
            if (loading == true) {
                binding.progressLogin.visibility = View.VISIBLE
            } else {
                binding.progressLogin.visibility = View.GONE
            }
        }

        val textSpan = "Don't have an account? Sign Up Now!"
        val stringSpan = SpannableString(textSpan)

        val black = ForegroundColorSpan(Color.BLACK)
        stringSpan.setSpan(black, 0, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val blue = ForegroundColorSpan(Color.parseColor("#001E6D"))
        stringSpan.setSpan(blue, 23, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                val animation = ActivityOptionsCompat.makeCustomAnimation(
                    this@LoginActivity,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                startActivity(intent, animation.toBundle())
                finish()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#001E6D")
                ds.isUnderlineText = false
            }
        }

        stringSpan.setSpan(clickableSpan, 23, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val bold = StyleSpan(Typeface.BOLD)
        stringSpan.setSpan(bold, 23, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvLogin.text = stringSpan
        binding.tvLogin.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().trim()
            val password = binding.edLoginPass.text.toString().trim()

            if (validateInputs(email, password)) {
                viewModel.saveLogin(email, password)
                viewModel.loginResult.observe(this) { response ->
                    if (response.success!!) {
                        val token = response.authResult?.token
                        if (token != null) {
                            val userModel = UserModel(email, token, true)
                            viewModel.saveSession(userModel)
                            showSuccessDialog()
                        } else {
                            showErrorDialog()
                        }
                    } else {
                        showErrorDialog()
                    }
                }
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                Toast.makeText(this, "Email is required.", Toast.LENGTH_SHORT).show()
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show()
                false
            }

            password.isEmpty() -> {
                Toast.makeText(this, "Password is required.", Toast.LENGTH_SHORT).show()
                false
            }

            else -> true
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Success!")
            setMessage("Yeay, you've already logged in!")
            setPositiveButton("Continue") { _, _ ->
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this).apply {
            setNegativeButton("Masuk Gagal") { _, _ ->
                Toast.makeText(context, "Masuk Gagal", Toast.LENGTH_SHORT).show()
            }
        }.create().show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}

