package com.example.myapplication.ui.number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.pref.MainRepository
import com.example.myapplication.data.response.DetailNumberResponse
import kotlinx.coroutines.launch

class DetailNumberViewModel(private val repository: MainRepository) : ViewModel() {
    private val _detail = MutableLiveData<DetailNumberResponse>()
    val detail: LiveData<DetailNumberResponse> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getNumberDetail(value: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val detailNumber = repository.getNumberDetail(value)
                _detail.value = detailNumber
            } catch (e: Exception) {
                _detail.value = DetailNumberResponse(null, false, e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}