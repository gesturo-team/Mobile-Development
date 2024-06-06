package com.example.myapplication.ui.number

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.data.response.NumberListItem
import com.example.myapplication.databinding.ActivityNumberBinding
import com.example.myapplication.factory.MainViewModelFactory
import com.example.myapplication.ui.main.MainViewModel

class NumberActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    private  lateinit var binding: ActivityNumberBinding
    private lateinit var adapter: NumberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvItem.layoutManager = layoutManager

        setSupportActionBar(binding.navNumber)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.navNumber.setNavigationOnClickListener{
            onBackPressed()
        }

       mainViewModel.getSession().observe(this) {user ->
            if (user.isLoggedIn) {
                mainViewModel.getNumber()
            }
        }

        mainViewModel.number.observe(this) {numberResponse ->
            if (numberResponse.success == true) {
                adapter.submitList(numberResponse.data?.wordList)
            } else {
                Toast.makeText(
                    this,
                    numberResponse.message ?: "Error fetching data!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        mainViewModel.isLoading.observe(this) {isLoading ->
            if (isLoading) {
                binding.progressNumber.visibility = View.VISIBLE
            } else {
                binding.progressNumber.visibility = View.GONE
            }
        }

        adapter = NumberAdapter()
        binding.rvItem.adapter = adapter
        adapter.setOnItemClickCallback(object : NumberAdapter.OnItemClickCallback {
            override fun onItemClicked(data: NumberListItem) {

            }
        })
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_number)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}