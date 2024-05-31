package com.example.pizzadelivery.ui.greeting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzadelivery.model.api.ResponseFirebase
import com.example.pizzadelivery.model.api.firebase.FirebaseRepository
import com.example.pizzadelivery.model.api.firebase.FirebaseRepositoryImpl
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class GreetingViewModel(
) : ViewModel(){


    private val firebaseRepository: FirebaseRepository = FirebaseRepositoryImpl()

    private val _loginLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val loginLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _loginLiveData

    private val _signUpLiveData = MutableLiveData<ResponseFirebase<FirebaseUser>?>(null)
    val signUpLiveData: LiveData<ResponseFirebase<FirebaseUser>?> = _signUpLiveData

    val currentUser: FirebaseUser?
        get() = firebaseRepository.currentUser

    init {
        if(firebaseRepository.currentUser != null){
            _loginLiveData.value = ResponseFirebase.Success(firebaseRepository.currentUser!!)
            onCleared()
        }
        Log.d("GreetingViewModel", "ViewModel created")
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginLiveData.value = ResponseFirebase.Loading
        val result = firebaseRepository.login(email, password)
        _loginLiveData.value = result
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        _signUpLiveData.value = ResponseFirebase.Loading
        val result = firebaseRepository.signUp(email, password)
        _signUpLiveData.value = result
    }
}