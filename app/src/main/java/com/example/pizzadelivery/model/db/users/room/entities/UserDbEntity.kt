package com.example.pizzadelivery.model.db.users.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [
        Index("email", unique = true)
    ])
data class UserDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "phone_number") var phoneNumber: String?,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String
)
