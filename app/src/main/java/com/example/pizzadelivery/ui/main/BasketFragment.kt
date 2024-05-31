package com.example.pizzadelivery.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzadelivery.R
import com.example.pizzadelivery.databinding.FragmentBasketBinding
import com.example.pizzadelivery.model.db.pizza.room.PizzaDbEntity
import com.example.pizzadelivery.ui.main.adapter.OrderAdapter
import com.example.pizzadelivery.ui.main.adapter.PizzaAdapter

class BasketFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.nameFragment.text = getString(R.string.basket)

        binding.toolbar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.orderPizzaItemsLiveData.observe(viewLifecycleOwner){ orderPizza ->
            showPizzaItems(orderPizza)
        }

        binding.button2.setOnClickListener {
            if(binding.recyclerBasket.adapter != null){
                viewModel.placeOrder()
                val address = binding.editTextAddress.text.toString()
                binding.editTextAddress.setText("")
                Toast.makeText(context, "Пицца заказана по адресу $address. Ожидайте ее в течение 30 минут", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showPizzaItems(pizzaItems: List<PizzaDbEntity>) {
        binding.recyclerBasket.layoutManager = LinearLayoutManager(context)
        binding.recyclerBasket.adapter = OrderAdapter(pizzaItems){pizza ->
            viewModel.deleteFromOrder(pizza)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}