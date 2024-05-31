package com.example.pizzadelivery.ui.main


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzadelivery.R
import com.example.pizzadelivery.databinding.FragmentMainPizzaBinding
import com.example.pizzadelivery.model.db.pizza.room.PizzaDbEntity
import com.example.pizzadelivery.ui.BaseFragment
import com.example.pizzadelivery.ui.main.adapter.PizzaAdapter
import com.example.pizzadelivery.ui.utils.SearchHistoryManager

class MainPizzaFragment : BaseFragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainPizzaBinding? = null
    private val binding get() = _binding!!

    private var isArrowUpPrice = true
    private var isArrowUpLetter = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainPizzaBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentPizzaList = emptyList<PizzaDbEntity>()
        val history = SearchHistoryManager.getHistory(requireContext()).toMutableList()
        val adapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            SearchHistoryManager.getHistory(requireContext()).reversed()
        )


        binding.toolbarMainPizza.editTextSearch.setAdapter(adapter)
        binding.toolbarMainPizza.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (binding.recyclerViewMain.adapter != null) {
                    filterRecyclerViewItems(query)
                } else {
                    binding.toolbarMainPizza.editTextSearch.text.clear()
                }
                if (query.length > 5 && !history.contains(query)) SearchHistoryManager.addToHistory(
                    requireContext(),
                    query
                )
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.toolbarMainPizza.buttonClear.visibility = View.GONE

                } else {
                    binding.toolbarMainPizza.buttonClear.visibility = View.VISIBLE
                }
            }
        })

        binding.toolbarMainPizza.buttonClear.setOnClickListener {
            binding.toolbarMainPizza.editTextSearch.text.clear()
            hideKeyboard()
        }

        viewModel.pizzaItemsLiveData.observe(viewLifecycleOwner) {
            currentPizzaList = it
            showPizzaItems(it)
            binding.customProgressBar.root.visibility = View.GONE
            binding.overlay.visibility = View.GONE
        }

        binding.refreshButton.setOnClickListener {
            binding.placeholderText.setText("Данные загружаются...")
            viewModel.fetchPizzas()
            binding.customProgressBar.root.visibility = View.VISIBLE
            binding.overlay.visibility = View.VISIBLE
            it.isEnabled = false
        }

        viewModel.orderPizzaItemsLiveData.observe(viewLifecycleOwner) { pizzas ->
            binding.toolbarMainPizza.countBasket.text = pizzas.size.toString()
        }

        binding.sortByLetter.setOnClickListener {

            isArrowUpLetter = !isArrowUpLetter
            var listForAdapter = currentPizzaList

            if(isArrowUpLetter == false){
                listForAdapter = listForAdapter.sortedByDescending { it.name }
            } else {
                listForAdapter = listForAdapter.sortedBy { it.name }
            }

            val drawable = if (isArrowUpLetter) {
                ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up)

            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.arrow_down)
            }

            binding.sortByLetter.setCompoundDrawablesWithIntrinsicBounds(
                null, null, drawable, null
            )
            showPizzaItems(listForAdapter)

        }

        binding.sortByPrice.setOnClickListener {
            isArrowUpPrice = !isArrowUpPrice
            var listForAdapter = currentPizzaList

            if(isArrowUpPrice == false){
                listForAdapter = listForAdapter.sortedByDescending { it.price }
            } else {
                listForAdapter = listForAdapter.sortedBy { it.price }
            }

            val drawable = if (isArrowUpPrice) {
                ContextCompat.getDrawable(requireContext(), R.drawable.arrow_up)

            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.arrow_down)
            }

            binding.sortByPrice.setCompoundDrawablesWithIntrinsicBounds(
                null, null, drawable, null
            )
            showPizzaItems(listForAdapter)
        }

    }

    private fun showPizzaItems(pizzaItems: List<PizzaDbEntity>) {
        if (pizzaItems.isNotEmpty()) {
            binding.placeholderText.visibility = View.GONE
            binding.refreshButton.visibility = View.GONE
            binding.recyclerViewMain.layoutManager = LinearLayoutManager(context)
            binding.recyclerViewMain.adapter = PizzaAdapter(pizzaItems) { pizza ->
                viewModel.addToOrder(pizza)
            }
        } else {
            binding.placeholderText.visibility = View.VISIBLE
            binding.refreshButton.visibility = View.VISIBLE
            binding.placeholderText.setText("Данные загружаются...")
            binding.refreshButton.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterRecyclerViewItems(query: String) {
        val adapter = binding.recyclerViewMain.adapter as PizzaAdapter
        adapter.filter.filter(query)
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}