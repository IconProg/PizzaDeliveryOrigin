package com.example.pizzadelivery.ui.main

import com.example.pizzadelivery.model.api.retrofit.PizzaImage

sealed class BannerItemsResult {
    object Loading : BannerItemsResult()
    data class Success(val bannerItems: List<PizzaImage>) : BannerItemsResult()
    data class Error(val errorMessage: String) : BannerItemsResult()
}