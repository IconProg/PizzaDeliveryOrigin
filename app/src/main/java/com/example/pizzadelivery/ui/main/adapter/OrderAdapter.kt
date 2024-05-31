package com.example.pizzadelivery.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzadelivery.R
import com.example.pizzadelivery.model.db.pizza.room.PizzaDbEntity
import com.squareup.picasso.Picasso

class OrderAdapter(private val originalPizza: List<PizzaDbEntity>, private val onDeleteFromBasketClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_PIZZA = 1
        private const val VIEW_TYPE_EMPTY = 0
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pizza_main_menu, parent, false)
            OrderViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
            EmptyViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return if (originalPizza.isEmpty()) {
            1
        } else {
            originalPizza.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OrderViewHolder) {
            val currentItem = originalPizza[position]
            holder.namePizzaTextView.text = currentItem.name
            holder.descriptionTextView.text = currentItem.description
            holder.priceButton.text = "Удалить пиццу"
            Picasso.get().load(currentItem.image).into(holder.imageView)

            holder.priceButton.setOnClickListener {
                onDeleteFromBasketClickListener(position)
            }
        } else if (holder is EmptyViewHolder) {
            holder.emptyTextView.text = holder.itemView.context.getString(R.string.no_order_found)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (originalPizza.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else {
            VIEW_TYPE_PIZZA
        }
    }
}