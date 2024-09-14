package com.example.anamnesisform.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anamnesisform.commons.UiState
import com.example.anamnesisform.data.local.repository.AnamnesisFormRepository
import com.example.anamnesisform.domain.model.AnamnesisForm
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AnamnesisFormRepository) : ViewModel() {
    private val _formError = MutableLiveData<String>()
    val formError: LiveData<String> get() = _formError

    private val _response = MutableLiveData<UiState<String>>()
    val response: LiveData<UiState<String>> get() = _response

    fun validate(form: AnamnesisForm) {
        validateInputs(form)
    }

    private fun save(form: AnamnesisForm) = viewModelScope.launch {
        _response.value = UiState.Loading(true)
        try {
            repository.insertForm(form)
            _response.value = UiState.Success("Dados da cliente salvos com sucesso")
        } catch (e: Exception) {
            _response.value =
                UiState.Error("Ocorreu um erro ao salvar dados no banco, tente novamente, e se o erro persistir, entre em contato com o suporte")
        } finally {
            _response.value = UiState.Loading(false)
        }
    }

    private fun validateInputs(form: AnamnesisForm) {
        if (form.nome.isNullOrEmpty()) {
            _formError.value = "O nome da cliente não pode estar vazio"
            return
        }

        if (form.celular.isNullOrEmpty()) {
            _formError.value = "O número de celular da cliente não pode estar vazio"
            return
        }

        if (form.email.isNullOrEmpty()) {
            _formError.value = "O e-mail da cliente não pode estar vazio"
            return
        }

        validateOperation(form)
    }

    private fun validateOperation(form: AnamnesisForm) {
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
            return
        }

        validateHabits(form)
    }

    private fun validateHabits(form: AnamnesisForm) {
        if (form.usaLentesContato == null) {
            _formError.value = "Você precisa informar se a cliente usa ou não lentes de contato"
            return
        }

        if (form.gestante == null) {
            _formError.value = "Você precisa informar se a cliente é ou não gestante"
            return
        }

        if (form.alergiaCosmeticosMaquiagens == null) {
            _formError.value =
                "Você precisa informar se a cliente tem ou não alergia a algum cosmético ou maquiagem"
            return
        } else if (form.alergiaCosmeticosMaquiagens == true && form.alergiaCosmetico.isNullOrBlank()) {
            _formError.value =
                "Você precisa informar a alergia a cosmético ou maquiagem que a cliente possui"
            return
        }

        if (form.alergiaProdutosHigienePessoal == null) {
            _formError.value =
                "Você precisa informar se a cliente tem ou não alergia a algum produto de higiene pessoal"
            return
        } else if (form.alergiaProdutosHigienePessoal == true && form.alergiaProdutosHigiene.isNullOrBlank()) {
            _formError.value =
                "Você precisa informar a alergia a produto de higiene pessoal que a cliente possui"
            return
        }

        if (form.fumante == null) {
            _formError.value = "Você precisa informar se a cliente é fumante ou não"
            return
        }

        if (form.lacrimejaConstante == null) {
            _formError.value = "Você precisa informar se a cliente lacrimeja constantemente ou não"
            return
        }

        if (form.sensibilidadeLuz == null) {
            _formError.value = "Você precisa informar se a cliente tem sensibilidade a luz ou não"
            return
        }

        if (form.fazTratamentoOcular == null) {
            _formError.value = "Você precisa informar se a cliente faz tratamento ocular ou não"
            return
        } else if (form.fazTratamentoOcular == true && form.tratamentoOcular.isNullOrBlank()) {
            _formError.value = "Você precisa informar o tratamento ocular feito pela cliente"
            return
        }

        if (form.fezCirurgiaOcularUltimos6Meses == null) {
            _formError.value =
                "Você precisa informar se a cliente fez alguma cirurgia ocular nos últimos 6 meses"
            return
        }

        save(form)
    }
}