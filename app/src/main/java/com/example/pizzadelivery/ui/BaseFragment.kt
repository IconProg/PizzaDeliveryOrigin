package com.example.pizzadelivery.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pizzadelivery.R
import com.mikhaellopez.circularimageview.CircularImageView

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsButton = view.findViewById<ImageButton>(R.id.settingsButton)
        val basketButton = view.findViewById<ImageButton>(R.id.basketButton)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
        basketButton.setOnClickListener {
            findNavController().navigate(R.id.basketFragment)
        }

    }
}