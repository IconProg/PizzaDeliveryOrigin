package com.example.pizzadelivery.model.api.firebase

import com.example.pizzadelivery.model.api.ResponseFirebase
import com.example.pizzadelivery.model.api.utils.await
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FirebaseRepositoryImpl: FirebaseRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseReference: DatabaseReference = Firebase.database.reference.child("user_data")

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): ResponseFirebase<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            ResponseFirebase.Success(result.user!!)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseFirebase.Failure(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): ResponseFirebase<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(email).build())?.await()
            val user_id = result.user?.uid
            if(user_id != null){
                firebaseReference.child(user_id).child("name").setValue(email)
            }
            ResponseFirebase.Success(result.user!!)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseFirebase.Failure(e)
        }
    }

    override suspend fun changeName(name: String) {
        if(currentUser != null){
            firebaseReference.child(currentUser!!.uid).child("name").setValue(name)
        }
    }

    override suspend fun getName(): String {
        var name = ""
        try {
            if (currentUser != null) {
                val snapshot = firebaseReference.child(currentUser!!.uid).child("name").get().await()
                name = snapshot.getValue(String::class.java) ?: ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if(name == "") name = currentUser?.email.toString()
        return name
    }


    override fun logOut() {
        firebaseAuth.signOut()
    }
}