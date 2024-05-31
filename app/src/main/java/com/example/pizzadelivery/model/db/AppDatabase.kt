package com.example.pizzadelivery.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pizzadelivery.model.db.users.room.entities.UserDbEntity
import com.example.pizzadelivery.model.db.users.room.UserDao


@Database(entities = [UserDbEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{

        private const val DATABASE_NAME= "app-database"

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}