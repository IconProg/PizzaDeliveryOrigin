package com.example.pizzadelivery.model.db.pizza.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PizzaDao {
    @Insert
    suspend fun insertPizza(pizza: PizzaDbEntity)

    @Query("SELECT * FROM pizza_table")
    suspend fun getAllPizzas(): List<PizzaDbEntity>
}