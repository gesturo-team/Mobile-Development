package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.pref.MainRepository
import com.example.myapplication.data.pref.UserRepository
import com.example.myapplication.data.response.AlphabetResponse
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _alphabet = MutableLiveData<AlphabetResponse>()
    val alphabet: LiveData<AlphabetResponse> = _alphabet

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getAlphabet() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val alphabetResponse = repository.getAlphabet()
                _alphabet.value = alphabetResponse
            } catch (e: Exception) {
                _alphabet.value = AlphabetResponse(null, success = false, message = e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}