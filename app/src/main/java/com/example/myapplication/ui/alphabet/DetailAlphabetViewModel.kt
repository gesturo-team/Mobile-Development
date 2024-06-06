package com.example.myapplication.ui.alphabet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.pref.MainRepository
import com.example.myapplication.data.response.DetailAlphabetResponse
import kotlinx.coroutines.launch

class DetailAlphabetViewModel(private val repository: MainRepository) : ViewModel() {
    private val _detail = MutableLiveData<DetailAlphabetResponse>()
    val detail: LiveData<DetailAlphabetResponse> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAlphabetDetail(value: String) {
        _isLoading.value = true
        try {
            viewModelScope.launch {
                val detailAlphabet = repository.getAlphabetDetail(value)
                _detail.value = detailAlphabet
            }
        } catch (e: Exception) {
            _detail.value = DetailAlphabetResponse(null, false, e.message)
        } finally {
            _isLoading.value = false
        }
    }
}