package com.example.myapplication.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.pref.MainRepository
import com.example.myapplication.di.MainInjection
import com.example.myapplication.ui.alphabet.DetailAlphabetViewModel
import com.example.myapplication.ui.main.MainViewModel

class MainViewModelFactory(private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailAlphabetViewModel::class.java) -> {
                DetailAlphabetViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context) =
            MainViewModelFactory(MainInjection.provideRepository(context))
    }
}