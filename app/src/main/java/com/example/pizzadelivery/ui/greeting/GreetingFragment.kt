package com.example.pizzadelivery.ui.greeting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.example.pizzadelivery.R
import com.example.pizzadelivery.model.api.ResponseFirebase
import com.example.pizzadelivery.ui.main.MainActivity

class GreetingFragment : Fragment() {
    private val viewModel: GreetingViewModel by activityViewModels()

    private lateinit var navController: NavController
    private lateinit var joinButton: Button
    private lateinit var enterButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_greeting, container, false)

        joinButton = view.findViewById(R.id.join_button) as Button
        enterButton = view.findViewById(R.id.enter_button) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModel.loginLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseFirebase.Success -> {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }
                is ResponseFirebase.Loading -> {

                }
                is ResponseFirebase.Failure -> {
                    Toast.makeText(context, response.exception.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        joinButton.setOnClickListener{ navController.navigate(R.id.action_greetingFragment_to_regFragment)}
        enterButton.setOnClickListener{ navController.navigate(R.id.action_greetingFragment_to_authFragment)}
    }
}