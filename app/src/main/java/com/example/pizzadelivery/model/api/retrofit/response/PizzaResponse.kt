package com.example.pizzadelivery.model.api.retrofit.response

import com.google.gson.annotations.SerializedName


class PizzaResponse {
    @SerializedName("image") lateinit var pizzaImage: String
}