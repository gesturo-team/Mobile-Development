package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.pref.MainRepository
import com.example.myapplication.data.pref.UserRepository
import com.example.myapplication.data.response.AlphabetQuizResponse
import com.example.myapplication.data.response.AlphabetResponse
import com.example.myapplication.data.response.NumberResponse
import com.example.myapplication.ui.quiz.alphabet.QuizAlphabetActivity
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _alphabet = MutableLiveData<AlphabetResponse>()
    val alphabet: LiveData<AlphabetResponse> = _alphabet

    private val _number = MutableLiveData<NumberResponse>()
    val number: LiveData<NumberResponse> = _number

    private val _quizAlphabet = MutableLiveData<AlphabetQuizResponse>()
    val quizAlphabet: LiveData<AlphabetQuizResponse> = _quizAlphabet

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

    fun getNumber(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val numberResponse = repository.getNumber()
                _number.value = numberResponse
            } catch (e: Exception) {
                _number.value = NumberResponse(null, success = false, message = e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getQuizAlphabet(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val quizAlphabetResponse = repository.getAlphabetQuiz()
                _quizAlphabet.value = quizAlphabetResponse
            } catch (e: Exception) {
                _quizAlphabet.value = AlphabetQuizResponse(null, false)
            } finally {
                _isLoading.value = false
            }
        }

    }
}