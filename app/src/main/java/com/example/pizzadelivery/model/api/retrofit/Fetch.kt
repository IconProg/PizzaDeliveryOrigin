package com.example.pizzadelivery.model.api.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pizzadelivery.model.api.retrofit.response.PizzaResponse
import com.imprarce.android.testtaskdelivery.data.api.ProductApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "ProductFetch"


class Fetch {
    private val productPizzaApi: ProductApi
    init {

        val retrofitPizza: Retrofit = Retrofit.Builder()
            .baseUrl("https://foodish-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        productPizzaApi = retrofitPizza.create(ProductApi::class.java)
    }


    fun fetchPizza(): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val flickrRequest: Call<PizzaResponse> = productPizzaApi.fetchPizza()

        flickrRequest.enqueue(object : Callback<PizzaResponse> {
            override fun onFailure(call: Call<PizzaResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch pizza", t)
            }

            override fun onResponse(
                call: Call<PizzaResponse>,
                response: Response<PizzaResponse>
            ) {
                val pizzaResponse: PizzaResponse? = response.body()
                var pizzaImage: String = pizzaResponse?.pizzaImage ?: ""

                responseLiveData.value = pizzaImage
                Log.d(TAG, "Response received pizza")
            }
        })

        return responseLiveData
    }

}