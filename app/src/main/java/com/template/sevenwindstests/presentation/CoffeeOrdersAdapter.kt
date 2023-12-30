package com.template.sevenwindstests.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.template.sevenwindstests.databinding.MenuOrderItemBinding
import com.template.sevenwindstests.model.CoffeeItem

class CoffeeOrdersAdapter: ListAdapter<CoffeeItem, CoffeeOrdersAdapter.CoffeeHolder>(
    object : DiffUtil.ItemCallback<CoffeeItem>() {
        override fun areItemsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem == newItem
        }

    }
) {
    class CoffeeHolder(val binding: MenuOrderItemBinding): RecyclerView.ViewHolder(binding.root)
    var onPlusItem : ((Int, CoffeeItem) -> Unit)? = null
    var onMinusItem : ((Int, CoffeeItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeHolder {
        return CoffeeHolder(
            MenuOrderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoffeeHolder, position: Int) {
        val coffeeItem  = getItem(position)
        holder.binding.apply {
            nameTv.text = coffeeItem.name
            priceTv.text = String.format(
                priceTv.text.toString(),
                coffeeItem.price
            )
            plusBtn.setOnClickListener {
                onPlusItem?.invoke(position, coffeeItem)
            }
            minusBtn.setOnClickListener {
                onMinusItem?.invoke(position, coffeeItem)
            }
            countTv.text = (coffeeItem.count ?: 0).toString()
        }
    }
}