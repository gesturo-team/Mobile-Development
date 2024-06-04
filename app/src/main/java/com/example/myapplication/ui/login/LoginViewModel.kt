package com.example.myapplication.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.pref.UserRepository
import com.example.myapplication.data.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

//class LoginViewModel(private val repository: UserRepository) : ViewModel() {
//    private val _loginResult = MutableLiveData<LoginResponse>()
//    val loginResult: LiveData<LoginResponse> = _loginResult
//
//    private val _isError = MutableLiveData<String>()
//    val isError: LiveData<String> = _isError
//
//    fun saveSession(userModel: UserModel) {
//        viewModelScope.launch {
//            repository.saveSession(userModel)
//        }
//    }
//
//    fun saveLogin(email: String, password: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.login(email, password)
//                _loginResult.value = response
//                if (response.success!!) {
//                    val token = response.authResult?.token
//                    token?.let {
//                        val userModel = UserModel(email, it, true)
//                        saveSession(userModel)
//                    }
//                } else {
//                    _loginResult.value = LoginResponse(false, null, "Failed to Login")
//                }
//            } catch (e:HttpException) {
//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
//                val errorMessage = errorBody.message
//                _isError.value = errorMessage ?: "Failed to login"
//            }
//        }
//    }
//}

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }

    fun saveLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _loginResult.value = response
                if (response.success!!) {
                    val token = response.authResult?.token
                    val fullName = response.authResult?.fullName
                    token?.let {
                        val userModel = UserModel(email, it, true, fullName)
                        saveSession(userModel)
                    }
                } else {
                    _loginResult.value = LoginResponse(false, null, "Failed to Login")
                }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                _isError.value = errorMessage ?: "Failed to login"
            }
        }
    }
}
