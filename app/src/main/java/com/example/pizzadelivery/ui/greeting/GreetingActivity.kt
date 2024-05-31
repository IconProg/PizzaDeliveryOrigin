package com.example.pizzadelivery.ui.greeting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pizzadelivery.R
import com.example.pizzadelivery.databinding.GreetingMainBinding

class GreetingActivity : AppCompatActivity(){
    private lateinit var binding : GreetingMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GreetingMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment : NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        navController = navHostFragment.navController
    }

}