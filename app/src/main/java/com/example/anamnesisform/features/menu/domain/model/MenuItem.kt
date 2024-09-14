package com.example.anamnesisform.features.menu.domain.model

import com.example.anamnesisform.commons.interfaces.IOnClickListener

data class MenuItem(
    val icon: Int,
    val title: String,
    val action: IOnClickListener
)
