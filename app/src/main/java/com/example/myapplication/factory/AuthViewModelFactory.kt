package com.example.myapplication.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.data.pref.UserRepository
import com.example.myapplication.di.AuthInjection
import com.example.myapplication.ui.login.LoginViewModel
import com.example.myapplication.ui.main.MainViewModel
import com.example.myapplication.ui.register.RegisterViewModel

class AuthViewModelFactory(private val repository: UserRepository) :
ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }



//            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
//                MainViewModel(repository) as T
//            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context) =
            AuthViewModelFactory(AuthInjection.provideRepository(context))
    }
}