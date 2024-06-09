package com.example.myapplication.ui.number

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
import com.example.myapplication.databinding.ActivityDetailNumberBinding
import com.example.myapplication.detection.CameraActivity
import com.example.myapplication.factory.MainViewModelFactory

class DetailNumberActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PICT = "extra_pict"
    }

    private lateinit var binding: ActivityDetailNumberBinding
    private val detailNumberViewModel by viewModels<DetailNumberViewModel> {
        MainViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.navDetailNumber)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.navDetailNumber.setNavigationOnClickListener{
            onBackPressed()
        }

        val value = intent.getStringExtra(EXTRA_NAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_NAME, value)

        detailNumberViewModel.isLoading.observe(this) {isLoading ->
            if (isLoading) {
                binding.progressDetailNumber.visibility = View.VISIBLE
            } else {
                binding.progressDetailNumber.visibility = View.GONE
            }
        }

        detailNumberViewModel.getNumberDetail(value.toString())

        detailNumberViewModel.detail.observe(this) {detailAlphabetResponse ->
            if (detailAlphabetResponse.success!!) {
                detailAlphabetResponse.data?.let { data ->
                    binding.tvNumber.text = data.value
                    Glide.with(binding.ivDetailAlphabet.context)
                        .load(data.urlImage)
                        .into(binding.ivDetailAlphabet)
                }
            }
        }

        binding.btnTry.setOnClickListener {
            val intentCamera = Intent(this@DetailNumberActivity, CameraActivity::class.java)
            startActivity(intentCamera)
        }
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_detail_number)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}