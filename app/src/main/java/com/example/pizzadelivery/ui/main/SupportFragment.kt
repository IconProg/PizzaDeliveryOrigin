package com.example.pizzadelivery.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pizzadelivery.R
import com.example.pizzadelivery.databinding.FragmentSupportBinding
import com.example.pizzadelivery.ui.BaseFragment

class SupportFragment : BaseFragment() {

    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSupportBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var name = ""

        binding.buttonCall.setOnClickListener {
            name = binding.editTextName.text.toString()
            if(name != ""){
                Toast.makeText(requireContext(), "Уважаемый $name, с вами свяжутся в ближайшее время", Toast.LENGTH_LONG).show()
                binding.editTextName.setText("")
                binding.editTextNumberPhone.setText("")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}