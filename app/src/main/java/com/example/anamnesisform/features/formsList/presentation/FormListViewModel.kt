package com.example.anamnesisform.features.formsList.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anamnesisform.commons.ui.UiState
import com.example.anamnesisform.data.local.repository.AnamnesisFormRepository
import com.example.anamnesisform.domain.model.AnamnesisForm
import kotlinx.coroutines.launch

class FormListViewModel(private val repository: AnamnesisFormRepository) : ViewModel() {
    private val _response = MutableLiveData<UiState<List<AnamnesisForm>>>()
    val response: LiveData<UiState<List<AnamnesisForm>>> get() = _response

    fun getForms() = viewModelScope.launch {
        _response.value = UiState.Loading(true)

        try {
            val forms = repository.getAllForms()
            if (forms.isNotEmpty()) {
                _response.value = UiState.Success(forms)
            } else {
                throw Exception("Não há nenhuma formulário cadastrado para ser exibido")
            }
        } catch (e: Exception) {
            _response.value = UiState.Error(e.message.toString())
        } finally {
            _response.value = UiState.Loading(false)
        }
    }
}