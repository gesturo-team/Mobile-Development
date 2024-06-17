package com.example.myapplication.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.pref.MainRepository
import com.example.myapplication.data.response.QuizData
import com.example.myapplication.data.response.QuizQuestionsItem
import com.example.myapplication.data.response.QuizResponse
import com.example.myapplication.data.response.AlphabetResponse
import com.example.myapplication.data.response.HistoryResponse
import com.example.myapplication.data.response.NumberResponse
import com.example.myapplication.data.response.SubmitAlphabetResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _alphabet = MutableLiveData<AlphabetResponse>()
    val alphabet: LiveData<AlphabetResponse> = _alphabet

    private val _number = MutableLiveData<NumberResponse>()
    val number: LiveData<NumberResponse> = _number

    private val _quizResponse = MutableLiveData<QuizResponse>()
    val quizResponse: LiveData<QuizResponse> = _quizResponse

    private val _submit = MutableLiveData<SubmitAlphabetResponse?>()
    val submit: MutableLiveData<SubmitAlphabetResponse?> = _submit

    private val _quiz = MutableLiveData<List<QuizQuestionsItem>>()
    val quiz: LiveData<List<QuizQuestionsItem>> = _quiz

    private val _historyResponse = MutableLiveData<HistoryResponse>()
    val historyResponse: LiveData<HistoryResponse> = _historyResponse

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
                _quizResponse.value = quizAlphabetResponse
            } catch (e: Exception) {
                _quizResponse.value = QuizResponse(null, false)
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun getQuizNumber(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val quizNumberResponse = repository.getNumberQuiz()
                _quizResponse.value = quizNumberResponse
            } catch (e: Exception) {
                _quizResponse.value = QuizResponse(null, false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getHistory(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val historyResponse = repository.getHistory()
                _historyResponse.value = historyResponse
            } catch (e: Exception) {
                _historyResponse.value = HistoryResponse(null, false)
            } finally {
                _isLoading.value = false
            }
        }
    }

fun submitAnswers(answer: QuizData) {
    _isLoading.value = true
    viewModelScope.launch {
        try {
            val response = repository.submitAnswer(answer)
            if (response.success == true) {
                _submit.postValue(response)
            } else {
                Log.e("MainViewModel", "Failed to submit answers: ${response.message}")
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        } finally {
            _isLoading.value = false
        }
    }
}
}