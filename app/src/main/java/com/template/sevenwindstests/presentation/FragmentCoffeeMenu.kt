package com.template.sevenwindstests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.template.sevenwindstests.R
import com.template.sevenwindstests.databinding.FragmentCoffeeMenuBinding
import com.template.sevenwindstests.model.CoffeeItemResultState
import kotlinx.coroutines.launch


class FragmentCoffeeMenu : Fragment() {
    private val binding by lazy {
        FragmentCoffeeMenuBinding.inflate(layoutInflater)
    }
    private val viewModel: CoffeeItemsViewModel by viewModels()
    private val locationId by lazy {
        arguments?.getInt(ID_EXTRA) ?: throw Exception("id is null")
    }
    private val adapter by lazy {
        CoffeeItemsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getCoffeeItem(locationId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    CoffeeItemResultState.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }

                    CoffeeItemResultState.Idle -> {

                    }

                    is CoffeeItemResultState.Success -> {
                        binding.recyclerViewCoffeeItems.adapter = adapter
                        binding.recyclerViewCoffeeItems.layoutManager =
                            GridLayoutManager(requireContext(), 2)
                        binding.recyclerViewCoffeeItems.itemAnimator = null
                        adapter.submitList(it.data)
                        adapter.onMinusItem = { position, coffeeItem ->
                            viewModel.onMinusItem(coffeeItem)
                            adapter.notifyItemChanged(position)
                        }
                        adapter.onPlusItem = { position, coffeeItem ->
                            viewModel.onPlusItem(coffeeItem)
                            adapter.notifyItemChanged(position)
                        }
                    }
                }
            }
        }
        binding.button.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragmentCoffeeMenu_to_fragmentMenuOrder,
                FragmentMenuOrder.ordersBundle(viewModel.orderList())
            )
        }
        return binding.root
    }

    companion object {
        private const val ID_EXTRA = "id"
        fun idBundle(id: Int) = Bundle().apply {
            putInt(ID_EXTRA, id)
        }
    }
}