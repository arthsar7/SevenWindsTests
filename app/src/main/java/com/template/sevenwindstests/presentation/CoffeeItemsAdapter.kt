package com.template.sevenwindstests.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.template.sevenwindstests.R
import com.template.sevenwindstests.databinding.MenuItemBinding
import com.template.sevenwindstests.model.CoffeeItem

class CoffeeItemsAdapter: ListAdapter<CoffeeItem, CoffeeItemsAdapter.CoffeeHolder>(
    object : DiffUtil.ItemCallback<CoffeeItem>() {
        override fun areItemsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean {
            return oldItem == newItem
        }

    }
) {
    class CoffeeHolder(val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root)
    var onPlusItem : ((Int, CoffeeItem) -> Unit)? = null
    var onMinusItem : ((Int, CoffeeItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeHolder {
        return CoffeeHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoffeeHolder, position: Int) {
        val coffeeItem  = getItem(position)
        val context = holder.itemView.context
        holder.binding.apply {
            nameTv.text = coffeeItem.name
            priceTv.text = String.format(
                priceTv.text.toString(),
                coffeeItem.price
            )
            Glide.with(context).load(coffeeItem.imageUrl)
                .error(R.drawable.coffee_placeholder)
                .into(imageIv)
            countTv.text = (coffeeItem.count ?: 0).toString()
            plusBtn.setOnClickListener {
                onPlusItem?.invoke(position, coffeeItem)
            }
            minusBtn.setOnClickListener {
                onMinusItem?.invoke(position, coffeeItem)
            }
        }
    }
}