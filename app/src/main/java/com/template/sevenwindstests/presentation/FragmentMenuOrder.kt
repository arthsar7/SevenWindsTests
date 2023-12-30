package com.template.sevenwindstests.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.template.sevenwindstests.databinding.FragmentCoffeeOrderBinding
import com.template.sevenwindstests.model.CoffeeItem
import kotlinx.coroutines.launch

class FragmentMenuOrder: Fragment() {
    private val binding by lazy {
        FragmentCoffeeOrderBinding.inflate(layoutInflater)
    }
    private val orders by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(ORDERS_EXTRA, CoffeeItem::class.java)
        }
        else {
            arguments?.getParcelableArrayList(ORDERS_EXTRA)
        } ?: throw Exception("orders is null")
    }
    private val viewModel by lazy {
        ViewModelProvider(this, CoffeeOrderViewModelFactory(orders))[CoffeeOrderViewModel::class.java]
    }
    private val adapter by lazy {
        CoffeeOrdersAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orders.collect {
                binding.recyclerViewOrders.adapter = adapter
                adapter.submitList(it)
            }
        }
        binding.recyclerViewOrders.itemAnimator = null
        adapter.onMinusItem = { position, coffeeItem ->
            viewModel.removeCoffeeItem(coffeeItem)
            adapter.notifyItemChanged(position)
        }
        adapter.onPlusItem = { position, coffeeItem ->
            viewModel.addCoffeeItem(coffeeItem)
            adapter.notifyItemChanged(position)
        }
        return binding.root
    }

    companion object {
        private const val ORDERS_EXTRA = "orders"
        fun ordersBundle(orders: List<CoffeeItem>) = Bundle().apply {
            putParcelableArrayList(ORDERS_EXTRA, ArrayList(orders))
        }
    }
}