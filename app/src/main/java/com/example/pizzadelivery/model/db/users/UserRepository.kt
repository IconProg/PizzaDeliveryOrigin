package com.example.pizzadelivery.model.db.users

import android.content.Context
import com.example.pizzadelivery.model.db.AppDatabase
import java.util.concurrent.Executors

class UserRepository private constructor(context: Context){

    private val appDatabase : AppDatabase = AppDatabase.getInstance(context)

    private val userDao = appDatabase.userDao()

    private val executor = Executors.newSingleThreadExecutor()





    companion object{
        private var INSTANCE: UserRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = UserRepository(context)
            }
        }

        fun get(): UserRepository {
            return INSTANCE ?:
            throw  java.lang.IllegalStateException("CrimeRepository must be initialized")
        }
    }
}