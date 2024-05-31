package com.example.pizzadelivery.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pizzadelivery.R
import com.example.pizzadelivery.databinding.FragmentSettingsBinding
import com.example.pizzadelivery.ui.greeting.GreetingActivity
import com.example.pizzadelivery.ui.utils.ThemeUtil.checkTheme
import com.example.pizzadelivery.ui.utils.ThemeUtil.toggleTheme

class SettingsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.nameFragment.text = "Настройки"
        binding.nightTheme.text = if(checkTheme(requireContext())) "Вкл" else "Выкл"

        binding.logOutButton.setOnClickListener {
            viewModel.logOut()

            val intent = Intent(requireActivity(), GreetingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }


        binding.toolbar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.userFromRoom.observe(viewLifecycleOwner){ response ->
            response?.let {
                binding.email.text = response
            }
        }

        binding.layoutNightTheme.setOnClickListener {
            toggleTheme(requireActivity())

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}