package com.template.sevenwindstests.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.template.sevenwindstests.databinding.CoffeeItemBinding
import com.template.sevenwindstests.model.LocationRespond

class CoffeeLocationsAdapter : ListAdapter<LocationRespond, CoffeeLocationsAdapter.LocationHolder>(
    object : DiffUtil.ItemCallback<LocationRespond>() {
        override fun areItemsTheSame(oldItem: LocationRespond, newItem: LocationRespond): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationRespond,
            newItem: LocationRespond
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    class LocationHolder(
        val binding: CoffeeItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
    var onLocationClickListener: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        return LocationHolder(
            CoffeeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val location = getItem(position)
        holder.binding.apply {
            nameTv.text = location.name
            root.setOnClickListener {
                onLocationClickListener?.invoke(location.id)
            }
        }
    }

}
