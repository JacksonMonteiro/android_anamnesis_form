package com.example.anamnesisform.features.menu.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anamnesisform.databinding.ItemMenuBinding
import com.example.anamnesisform.features.menu.domain.model.MenuItem

class MenuAdapter(private val items: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.menuIcon.setImageResource(item.icon)
        holder.binding.menuTitle.text = item.title
        holder.binding.menuCard.setOnClickListener { item.action.onClick(position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}