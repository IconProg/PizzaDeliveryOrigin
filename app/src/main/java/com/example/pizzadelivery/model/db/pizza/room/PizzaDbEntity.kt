package com.example.pizzadelivery.model.db.pizza.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pizza_table")
data class PizzaDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val description: String,
    val image: String,
    val price: Double
)