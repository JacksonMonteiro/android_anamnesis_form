package com.example.anamnesisform.features.menu.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anamnesisform.R
import com.example.anamnesisform.commons.interfaces.IOnClickListener
import com.example.anamnesisform.databinding.FragmentMenuBinding
import com.example.anamnesisform.features.menu.domain.model.MenuItem
import com.example.anamnesisform.features.menu.presentation.adapter.MenuAdapter

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }

    private fun setupMenu() {
        adapter = MenuAdapter(getMenuItems())
        binding.rvMenu.adapter = adapter
        binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun getMenuItems(): List<MenuItem> = listOf(
        MenuItem(
            icon = R.drawable.ic_add,
            title = "Preencher Formulário",
            action = object : IOnClickListener {
                override fun onClick(position: Int) {
                    findNavController().navigate(R.id.action_menu_to_form)
                }
            }),
        MenuItem(
            icon = R.drawable.ic_list,
            title = "Ver Formulários",
            action = object : IOnClickListener {
                override fun onClick(position: Int) {

                }
            })
    )
}
