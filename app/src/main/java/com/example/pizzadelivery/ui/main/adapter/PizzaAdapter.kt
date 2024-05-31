package com.example.pizzadelivery.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzadelivery.R
import com.example.pizzadelivery.model.db.pizza.room.PizzaDbEntity
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class PizzaAdapter(private val originalPizza: List<PizzaDbEntity>, private val onAddToBasketClickListener: (PizzaDbEntity) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var filteredPizza: List<PizzaDbEntity> = originalPizza

    companion object {
        private const val VIEW_TYPE_PIZZA = 1
        private const val VIEW_TYPE_EMPTY = 0
    }

    inner class PizzaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.pizza_image)
        val namePizzaTextView: TextView = itemView.findViewById(R.id.pizza_name)
        val descriptionTextView: TextView = itemView.findViewById(R.id.pizza_text)
        val priceButton: Button = itemView.findViewById(R.id.pizza_price)
    }

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emptyTextView: TextView = itemView.findViewById(R.id.empty_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_PIZZA) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pizza_main_menu, parent, false)
            PizzaViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
            EmptyViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return if (filteredPizza.isEmpty()) {
            1
        } else {
            filteredPizza.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PizzaViewHolder) {
            val currentItem = filteredPizza[position]
            holder.namePizzaTextView.text = currentItem.name
            holder.descriptionTextView.text = currentItem.description
            holder.priceButton.text = currentItem.price.toString()
            Picasso.get().load(currentItem.image).into(holder.imageView)

            holder.priceButton.setOnClickListener {
                onAddToBasketClickListener(currentItem)
            }
        } else if (holder is EmptyViewHolder) {
            holder.emptyTextView.text = holder.itemView.context.getString(R.string.no_pizza_found)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (filteredPizza.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else {
            VIEW_TYPE_PIZZA
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<PizzaDbEntity>()
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                for (pizza in originalPizza) {
                    if (pizza.name.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(pizza)
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredPizza = results?.values as List<PizzaDbEntity>
                notifyDataSetChanged()
            }
        }
    }
}