package com.example.myapplication.ui.alphabet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAlphabetBinding
import com.example.myapplication.databinding.ActivityDetailAlphabetBinding
import com.example.myapplication.detection.CameraActivity
import com.example.myapplication.factory.MainViewModelFactory

class DetailAlphabetActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PICT = "extra_pict"
    }

    private lateinit var binding: ActivityDetailAlphabetBinding
    private val detailAlphabetViewModel by viewModels<DetailAlphabetViewModel> {
        MainViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.navDetailAlphabet)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.navDetailAlphabet.setNavigationOnClickListener{
            onBackPressed()
        }

        val value = intent.getStringExtra(EXTRA_NAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_NAME, value)

        detailAlphabetViewModel.isLoading.observe(this) {isLoading ->
            if (isLoading) {
                binding.progressDetailAlphabet.visibility = View.VISIBLE
            } else {
                binding.progressDetailAlphabet.visibility = View.GONE
            }
        }

        detailAlphabetViewModel.getAlphabetDetail(value.toString())

        detailAlphabetViewModel.detail.observe(this) {detailAlphabetResponse ->
            if (detailAlphabetResponse.success!!) {
                detailAlphabetResponse.data?.let { data ->
                    binding.tvAlphabet.text = data.value
                    Glide.with(binding.ivDetailAlphabet.context)
                        .load(data.urlImage)
                        .into(binding.ivDetailAlphabet)
                }
            }
        }

        binding.btnTry.setOnClickListener {
            val intentCamera = Intent(this@DetailAlphabetActivity, CameraActivity::class.java)
            startActivity(intentCamera)
        }
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_detail_alphabet)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}