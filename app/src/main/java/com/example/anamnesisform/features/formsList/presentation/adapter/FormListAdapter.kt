package com.example.anamnesisform.features.formsList.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anamnesisform.commons.interfaces.IOnClickListener
import com.example.anamnesisform.databinding.ItemFormsBinding
import com.example.anamnesisform.domain.model.AnamnesisForm

class FormListAdapter(
    private val items: List<AnamnesisForm>,
    private val action: IOnClickListener) : RecyclerView.Adapter<FormListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFormsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFormsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.formId.text = item.id.toString()
        holder.binding.formClientName.text = item.nome
        holder.binding.formClientPhone.text = item.celular
        holder.binding.formCard.setOnClickListener { action.onClick(position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int) : AnamnesisForm {
        return items[position]
    }
}