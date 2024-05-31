package com.example.pizzadelivery.model.db.pizza.room

interface PizzaRepository {
    suspend fun insertPizza(pizza: PizzaDbEntity)

    suspend fun getAllPizzas(): List<PizzaDbEntity>
}