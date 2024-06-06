package com.example.myapplication.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.pref.UserRepository
import com.example.myapplication.data.response.ErrorsItem
import com.example.myapplication.data.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _errorReg = MutableLiveData<List<ErrorsItem?>?>()
    val errorReg: LiveData<List<ErrorsItem?>?> get() = _errorReg

    fun getRegister(firstName: String, lastName:String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(firstName, lastName, email, password)
                _registerResult.value = response
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                val errorMessage = errorBody.errors
                _errorReg.value = errorMessage
                Log.e("SendRegistrationData", "Error sending registration data: ${errorMessage?.joinToString { it?.msg ?: "Unknown error" }}")
            } catch (e: Exception) {
                Log.e("SendRegistrationData", "Unexpected error: ${e.message}")
                val unknownError = listOf(ErrorsItem(msg = "Unexpected error occurred"))
                _errorReg.value = unknownError
                RegisterResponse(success = false, errors = unknownError)
            }
        }
    }
}