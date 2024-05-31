package com.example.pizzadelivery.model.api.firebase

import com.google.firebase.auth.FirebaseUser
import com.example.pizzadelivery.model.api.ResponseFirebase

interface FirebaseRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): ResponseFirebase<FirebaseUser>
    suspend fun signUp(email: String, password: String): ResponseFirebase<FirebaseUser>

    suspend fun changeName(name: String)

    suspend fun getName() : String

    fun logOut()
}