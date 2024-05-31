package com.example.pizzadelivery.model.db.pizza

import com.example.pizzadelivery.model.db.pizza.room.PizzaDao
import com.example.pizzadelivery.model.db.pizza.room.PizzaDbEntity
import com.example.pizzadelivery.model.db.pizza.room.PizzaRepository

class PizzaRepositoryImpl(private val pizzaDao: PizzaDao) : PizzaRepository {

    override suspend fun insertPizza(pizza: PizzaDbEntity) {
        pizzaDao.insertPizza(pizza)
    }

    override suspend fun getAllPizzas(): List<PizzaDbEntity> {
        return pizzaDao.getAllPizzas()
    }
}