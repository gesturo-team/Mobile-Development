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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.factory.AuthViewModelFactory
import com.example.myapplication.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

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

        registerViewModel.errorReg.observe(this) { errors ->
            errors?.let {
                val errorMessage = it.joinToString(separator = "\n") { errorItem ->
                    errorItem?.msg ?: "Unknown error"
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.loading.observe(this) { loading ->
            if (loading == true) {
                binding.progressRegister.visibility = View.VISIBLE
            } else {
                binding.progressRegister.visibility = View.GONE
            }
        }

        registerViewModel.registerResult.observe(this) { response ->
            response?.let {
                if (it.success == true) {
                    showSuccessDialog()
                } else {
                    val errorMessage = it.errors?.joinToString(separator = "\n") { errorItem ->
                        errorItem?.msg ?: "Unknown error"
                    } ?: "Email is already registered"
                    showToast(errorMessage)
                }
            } ?: run {
                showToast("Unexpected error")
            }
        }

    }

    private fun showToast(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessDialog() {
        val firstName = binding.edRegisterFirstName.text.toString()
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Hi $firstName, your account is ready. Log in now and start learning!")
            setPositiveButton("Continue") { _, _ ->
                loginActivity()
                finish()
            }
            create()
            show()
        }
    }

    private fun loginActivity() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }

    private fun validateInput(firstName: String, lastName: String, email: String, password: String) : Boolean {
        return when {
            firstName.isEmpty() -> {
                Toast.makeText(this, "Please enter your first name.", Toast.LENGTH_SHORT).show()
                false
            }
            lastName.isEmpty() -> {
                Toast.makeText(this, "Please enter your last name.", Toast.LENGTH_SHORT).show()
                false
            }email.isEmpty() -> {
                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
                false
            }password.isEmpty() -> {
                Toast.makeText(this, "Please enter your last password.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun setupAction() {
        binding.btnReg.setOnClickListener {
            val firstName = binding.edRegisterFirstName.text.toString().trim()
            val lastName = binding.edRegisterLastName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPass.text.toString().trim()

            if (validateInput(firstName,lastName, email, password)) {
                postRegister()
            }
        }
    }

    private fun postRegister() {
        binding.apply {
            registerViewModel.getRegister(
                edRegisterFirstName.text.toString().trim(),
                edRegisterLastName.text.toString().trim(),
                edRegisterEmail.text.toString().trim(),
                edRegisterPass.text.toString().trim()
            )
        }
    }
}