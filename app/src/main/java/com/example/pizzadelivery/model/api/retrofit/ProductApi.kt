package com.imprarce.android.testtaskdelivery.data.api

import com.example.pizzadelivery.model.api.retrofit.response.PizzaResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi{

    @GET("/api/images/pizza/")
    fun fetchPizza(): Call<PizzaResponse>
}