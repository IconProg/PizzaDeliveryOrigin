package com.example.pizzadelivery.model.db.users.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pizzadelivery.model.db.users.room.entities.UserDbEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): List<UserDbEntity>
    @Query("SELECT * FROM user where id=(:id)")
    fun getUser(id: Int): UserDbEntity
    @Insert
    fun addUser(userDbEntity: UserDbEntity)
    @Update
    fun updateUser(userDbEntity: UserDbEntity)
}