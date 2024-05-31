package com.example.pizzadelivery.ui.greeting

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.pizzadelivery.R
import com.example.pizzadelivery.databinding.FragmentAuthBinding
import com.example.pizzadelivery.model.api.ResponseFirebase
import com.example.pizzadelivery.ui.main.MainActivity

class AuthFragment : androidx.fragment.app.Fragment() {
    private val viewModel: GreetingViewModel by activityViewModels()

    private var email: String = ""
    private var password: String = ""


    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.enterButton.setOnClickListener {
            email = binding.editTextEmail.text.toString()
            password = binding.editTextPassword.text.toString()
            Log.d("AUTH", "$email")
            viewModel.login(email, password)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}