package com.example.pizzadelivery.ui.greeting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.pizzadelivery.R
import com.example.pizzadelivery.databinding.FragmentRegBinding
import com.example.pizzadelivery.model.api.ResponseFirebase
import com.example.pizzadelivery.ui.main.MainActivity

class RegFragment : Fragment() {
    private val viewModel: GreetingViewModel by activityViewModels()

    private var email: String = ""
    private var password: String = ""
    private var confirmedPassword: String = ""

    private var _binding: FragmentRegBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegBinding.inflate(inflater, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signUpLiveData.observe(viewLifecycleOwner){
                response ->
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

        binding.regButton.setOnClickListener {
            email = binding.editTextEmail.text.toString()
            password = binding.editTextPassword.text.toString()
            confirmedPassword = binding.editTextConfirmPassword.text.toString()
            if(password.equals(confirmedPassword, ignoreCase = true)){
                viewModel.signUp(email, password)
            } else {
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}