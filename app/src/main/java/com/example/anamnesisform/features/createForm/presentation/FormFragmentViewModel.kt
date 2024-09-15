package com.example.anamnesisform.features.createForm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anamnesisform.commons.ui.UiState
import com.example.anamnesisform.data.local.repository.AnamnesisFormRepository
import com.example.anamnesisform.domain.model.AnamnesisForm
import kotlinx.coroutines.launch

class FormFragmentViewModel(private val repository: AnamnesisFormRepository) : ViewModel() {
    private val _formError = MutableLiveData<String>()
    val formError: LiveData<String> get() = _formError

    private val _saveResponse = MutableLiveData<UiState<String>>()
    val saveResponse: LiveData<UiState<String>> get() = _saveResponse

    private val _updateResponse = MutableLiveData<UiState<String>>()
    val updateResponse: LiveData<UiState<String>> get() = _updateResponse

    private val _deleteResponse = MutableLiveData<UiState<String>>()
    val deleteResponse: LiveData<UiState<String>> get() = _deleteResponse

    fun validate(form: AnamnesisForm, isUpdate: Boolean) {
        if (validateInputs(form) && validateOperation(form) && validateHabits(form)) {
            if (isUpdate) {
                update(form)
            } else {
                save(form)
            }
        }
    }

    fun delete(form: AnamnesisForm) = viewModelScope.launch {
        _deleteResponse.value = UiState.Loading(true)
        try {
            repository.deleteForm(form)
            _deleteResponse.value = UiState.Success("Formulário deletado com sucesso")
        } catch (e: Exception) {
            _deleteResponse.value =
                UiState.Error("Ocorreu um erro ao salvar dados no banco, tente novamente, e se o erro persistir, entre em contato com o suporte")
        } finally {
            _deleteResponse.value = UiState.Loading(false)
        }
    }

    private fun save(form: AnamnesisForm) = viewModelScope.launch {
        _saveResponse.value = UiState.Loading(true)
        try {
            repository.insertForm(form)
            _saveResponse.value = UiState.Success("Dados da cliente salvos com sucesso")
        } catch (e: Exception) {
            _saveResponse.value =
                UiState.Error("Ocorreu um erro ao salvar dados no banco, tente novamente, e se o erro persistir, entre em contato com o suporte")
        } finally {
            _saveResponse.value = UiState.Loading(false)
        }
    }

    private fun update(form: AnamnesisForm) = viewModelScope.launch {
        _updateResponse.value = UiState.Loading(true)
        try {
            repository.updateForm(form)
            _updateResponse.value = UiState.Success("Dados da cliente salvos com sucesso")
        } catch (e: Exception) {
            _updateResponse.value =
                UiState.Error("Ocorreu um erro ao salvar dados no banco, tente novamente, e se o erro persistir, entre em contato com o suporte")
        } finally {
            _updateResponse.value = UiState.Loading(false)
        }
    }

    private fun validateInputs(form: AnamnesisForm) : Boolean {
        if (form.nome.isNullOrEmpty()) {
            _formError.value = "O nome da cliente não pode estar vazio"
            return false
        }

        if (form.celular.isNullOrEmpty()) {
            _formError.value = "O número de celular da cliente não pode estar vazio"
            return false
        }

        if (form.email.isNullOrEmpty()) {
            _formError.value = "O e-mail da cliente não pode estar vazio"
            return false
        }

        return true
    }

    private fun validateOperation(form: AnamnesisForm) : Boolean {
        val operations = listOf(
            form.fioAFio,
            form.volumeRusso,
            form.hibrido,
            form.megaVolume,
            form.volumeBrasileiro,
            form.express
        )

        if (operations.all { it == false }) {
            _formError.value = "Você precisa marcar pelo menos um procedimento a ser realizado"
            return false
        }

        return true
    }

    private fun validateHabits(form: AnamnesisForm) : Boolean {
        if (form.usaLentesContato == null) {
            _formError.value = "Você precisa informar se a cliente usa ou não lentes de contato"
            return false
        }

        if (form.gestante == null) {
            _formError.value = "Você precisa informar se a cliente é ou não gestante"
            return false
        }

        if (form.alergiaCosmeticosMaquiagens == null) {
            _formError.value =
                "Você precisa informar se a cliente tem ou não alergia a algum cosmético ou maquiagem"
            return false
        } else if (form.alergiaCosmeticosMaquiagens == true && form.alergiaCosmetico.isNullOrBlank()) {
            _formError.value =
                "Você precisa informar a alergia a cosmético ou maquiagem que a cliente possui"
            return false
        }

        if (form.alergiaProdutosHigienePessoal == null) {
            _formError.value =
                "Você precisa informar se a cliente tem ou não alergia a algum produto de higiene pessoal"
            return false
        } else if (form.alergiaProdutosHigienePessoal == true && form.alergiaProdutosHigiene.isNullOrBlank()) {
            _formError.value =
                "Você precisa informar a alergia a produto de higiene pessoal que a cliente possui"
            return false
        }

        if (form.fumante == null) {
            _formError.value = "Você precisa informar se a cliente é fumante ou não"
            return false
        }

        if (form.lacrimejaConstante == null) {
            _formError.value = "Você precisa informar se a cliente lacrimeja constantemente ou não"
            return false
        }

        if (form.sensibilidadeLuz == null) {
            _formError.value = "Você precisa informar se a cliente tem sensibilidade a luz ou não"
            return false
        }

        if (form.fazTratamentoOcular == null) {
            _formError.value = "Você precisa informar se a cliente faz tratamento ocular ou não"
            return false
        } else if (form.fazTratamentoOcular == true && form.tratamentoOcular.isNullOrBlank()) {
            _formError.value = "Você precisa informar o tratamento ocular feito pela cliente"
            return false
        }

        if (form.fezCirurgiaOcularUltimos6Meses == null) {
            _formError.value =
                "Você precisa informar se a cliente fez alguma cirurgia ocular nos últimos 6 meses"
            return false
        }

        return true
    }
}