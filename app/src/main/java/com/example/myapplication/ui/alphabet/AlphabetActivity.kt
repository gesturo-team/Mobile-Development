package com.example.myapplication.ui.alphabet

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.data.response.WordListItem
import com.example.myapplication.databinding.ActivityAlphabetBinding
import com.example.myapplication.factory.MainViewModelFactory
import com.example.myapplication.ui.main.MainViewModel

class AlphabetActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityAlphabetBinding
    private lateinit var adapter: AlphabetAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvItem.layoutManager = layoutManager

        setSupportActionBar(binding.navAlphabet)

        //activate back button
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.navAlphabet.setNavigationOnClickListener{
            onBackPressed()
        }

        mainViewModel.getSession().observe(this) {user ->
            if (user.isLoggedIn) {
                mainViewModel.getAlphabet()
            }
        }

        mainViewModel.alphabet.observe(this) {alphabetResponse ->
            if (alphabetResponse.success == true) {
                adapter.submitList(alphabetResponse.data?.wordList)
            } else {
                Toast.makeText(
                    this,
                    alphabetResponse.message ?: "Error fetching data!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        mainViewModel.isLoading.observe(this) {isLoading ->
            if (isLoading) {
                binding.progressAlphabet.visibility = View.VISIBLE
            } else {
                binding.progressAlphabet.visibility = View.GONE
            }
        }

        adapter = AlphabetAdapter()
        binding.rvItem.adapter = adapter
        adapter.setOnItemClickCallback(object : AlphabetAdapter.OnItemClickCallback {
            override fun onItemClicked(data: WordListItem) {

            }
        })
    }
}