package com.example.pizzadelivery.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzadelivery.model.api.firebase.FirebaseRepository
import com.example.pizzadelivery.model.api.firebase.FirebaseRepositoryImpl
import com.example.pizzadelivery.model.api.retrofit.Fetch
import com.example.pizzadelivery.model.db.pizza.room.PizzaDbEntity
import com.example.pizzadelivery.model.db.users.room.entities.UserDbEntity
import com.example.pizzadelivery.ui.utils.PizzaDetails
import com.example.pizzadelivery.ui.utils.pizzaDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val firebaseRepository: FirebaseRepository = FirebaseRepositoryImpl()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _pizzaItemsLiveData: MutableLiveData<List<PizzaDbEntity>> = MutableLiveData()
    val pizzaItemsLiveData: LiveData<List<PizzaDbEntity>> = _pizzaItemsLiveData

    private val _orderPizzaItemsLiveData: MutableLiveData<MutableList<PizzaDbEntity>> = MutableLiveData()
    val orderPizzaItemsLiveData: LiveData<MutableList<PizzaDbEntity>> = _orderPizzaItemsLiveData

    private val _userFromRoom = MutableLiveData<String>()
    val userFromRoom: LiveData<String> = _userFromRoom

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
    init {
        _pizzaItemsLiveData.value = mutableListOf()
        getUser()
        fetchPizzas()
    }

    fun fetchPizzas() {
        val pizzas = mutableListOf<PizzaDbEntity>()
        viewModelScope.launch {
            val images = fetchPizzaImages()
            while (images.isEmpty()){
                delay(1000L)
            }

            for (i in images.indices) {
                val details = pizzaDetails.getOrNull(i) ?: PizzaDetails("Пицца ${i + 1}", "Описание пиццы ${i + 1}", 399.0)
                val pizza = PizzaDbEntity(
                    name = details.name,
                    description = details.description,
                    image = images[i],
                    price = details.price
                )
                pizzas.add(pizza)
            }
            _pizzaItemsLiveData.postValue(pizzas)
        }
    }

    private suspend fun fetchPizzaImages(): List<String> {
        return withContext(Dispatchers.Main) {
            val images = mutableListOf<String>()
            repeat(9) {
                Fetch().fetchPizza().observeForever { pizza ->
                    images.add(pizza.toString())
                }
            }
            images
        }
    }

    fun getUser() = viewModelScope.launch {
        _userFromRoom.value = currentUser?.email
    }

    fun logOut(){
        firebaseRepository.logOut()
    }

    fun addToOrder(pizza: PizzaDbEntity) {
        val currentOrder = _orderPizzaItemsLiveData.value ?: mutableListOf()
        currentOrder.add(pizza)
        _orderPizzaItemsLiveData.value = currentOrder
    }

    fun deleteFromOrder(index: Int) {
        val currentOrder = _orderPizzaItemsLiveData.value ?: mutableListOf()
        currentOrder.removeAt(index)
        _orderPizzaItemsLiveData.value = currentOrder
    }

    fun placeOrder() {
        _orderPizzaItemsLiveData.value = mutableListOf()
    }
}