package com.template.sevenwindstests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.template.sevenwindstests.R
import com.template.sevenwindstests.databinding.FragmentNearCoffeeBinding
import com.template.sevenwindstests.model.CoffeeLocationResultState
import kotlinx.coroutines.launch

class FragmentCoffeeLocations: Fragment() {
    private val binding by lazy {
        FragmentNearCoffeeBinding.inflate(layoutInflater)
    }
    private val viewModel: CoffeeLocationsViewModel by viewModels()
    private val coffeeLocationsAdapter by lazy {
        CoffeeLocationsAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when(it) {
                    CoffeeLocationResultState.Error -> { }
                    CoffeeLocationResultState.Idle -> { }
                    is CoffeeLocationResultState.Success -> {
                        val locations = it.response
                        binding.recyclerViewOrders.adapter = coffeeLocationsAdapter
                        coffeeLocationsAdapter.submitList(locations)
                        coffeeLocationsAdapter.onLocationClickListener = { id ->
                            findNavController()
                                .navigate(
                                    R.id.action_fragmentCoffeeLocations_to_fragmentCoffeeMenu,
                                    FragmentCoffeeMenu.idBundle(id)
                                )
                        }
                    }
                }
            }
        }
        return binding.root
    }
}