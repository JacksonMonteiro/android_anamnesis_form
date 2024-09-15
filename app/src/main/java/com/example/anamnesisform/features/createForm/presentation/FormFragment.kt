package com.example.anamnesisform.features.createForm.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.anamnesisform.R
import com.example.anamnesisform.commons.ui.UiState
import com.example.anamnesisform.databinding.FragmentFormBinding
import com.example.anamnesisform.domain.model.AnamnesisForm
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
    private val viewModel: FormFragmentViewModel by inject()

    private var loadingDialog: AlertDialog? = null
    private var id: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        setupBundle()
    }

    //region SETUPS
    private fun setupListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.validate(makeForm(), false)
        }

        binding.updateButton.setOnClickListener {
            viewModel.validate(makeForm(), true)
        }

        binding.deleteButton.setOnClickListener { viewModel.delete(makeForm()) }

        binding.rgAlergiaCosmeticos.setOnCheckedChangeListener { _, id ->
            binding.etAlergiaCosmeticos.isVisible = id == R.id.radio_alergia_cosmeticos_sim
        }

        binding.rgAlergiaHigiene.setOnCheckedChangeListener { _, id ->
            binding.etAlergiaHigiene.isVisible = id == R.id.radio_alergia_higiene_sim
        }

        binding.rgTratamentoOcular.setOnCheckedChangeListener { _, id ->
            binding.etTratamentoOcular.isVisible = id == R.id.radio_tratamento_ocular_sim
        }
    }

    private fun setupObservers() {
        viewModel.formError.observe(viewLifecycleOwner) { error ->
            showSnackbar(error)
        }

        viewModel.saveResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> handleSuccess("Os dados do formulário foram salvos com sucesso")
                is UiState.Error -> showSnackbar(state.message)
                is UiState.Loading -> handleLoading(state.isLoading)
            }
        }

        viewModel.updateResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> handleSuccess("Os dados do formulário foram atualizados com sucesso")
                is UiState.Error -> showSnackbar(state.message)
                is UiState.Loading -> handleLoading(state.isLoading)
            }
        }

        viewModel.deleteResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> handleSuccess("O formulário foi deletado com sucesso")
                is UiState.Error -> showSnackbar(state.message)
                is UiState.Loading -> handleLoading(state.isLoading)
            }
        }
    }

    private fun setupBundle() {
        val form = arguments?.getSerializable("form") as? AnamnesisForm
        if (form != null) {
            setupId(form.id)
            handleFields(form)
        }
    }

    private fun setupId(identifier: Long) {
        id = identifier
    }
    //endregion

    //region Handlers
    private fun handleSuccess(message: String) {
        AlertDialog.Builder(requireContext()).setTitle("SUCESSO!")
            .setMessage(message)
            .setPositiveButton("Voltar") { _, _ ->
                requireActivity().supportFragmentManager.popBackStack()
            }
            .create().show()
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else hideLoadingDialog()
    }

    private fun handleFields(form: AnamnesisForm) {
        handleFormFields()
        handleFormInputs(form)
        handleCheckboxes(form)
        handleRadiosButtons(form)
    }

    private fun handleFormFields() {
        binding.saveButton.isVisible = false
        binding.updateButton.isVisible = true
        binding.deleteButton.isVisible = true
        binding.llTermoResponsabiliade.isVisible = false
    }

    private fun handleFormInputs(form: AnamnesisForm) {
        binding.etNome.setText(form.nome)
        binding.etDataNascimento.setText(form.dataNascimento)
        binding.etProfissao.setText(form.profissao)
        binding.etCelular.setText(form.celular)
        binding.etEmail.setText(form.email)
    }

    private fun handleCheckboxes(form: AnamnesisForm) {

        binding.checkboxBlefarite.isChecked = form.blefarite == true
        binding.checkboxTercolCalazio.isChecked = form.tercolCalazio == true
        binding.checkboxConjutivite.isChecked = form.conjutivite == true
        binding.checkboxDiabetes.isChecked = form.diabetes == true
        binding.checkboxGlaucoma.isChecked = form.glaucoma == true

        binding.checkboxFioAFio.isChecked = form.fioAFio == true
        binding.checkboxVolumeRusso.isChecked = form.volumeRusso == true
        binding.checkboxHibrido.isChecked = form.hibrido == true
        binding.checkboxMegaVolume.isChecked = form.megaVolume == true
        binding.checkboxVolumeBrasileiro.isChecked = form.volumeBrasileiro == true
        binding.checkboxExpress.isChecked = form.express == true
    }

    private fun handleRadiosButtons(form: AnamnesisForm) {
        if (form.usaLentesContato == true) {
            binding.radioUsaLentesSim.isChecked = true
        } else {
            binding.radioUsaLentesSim.isChecked = true
        }

        if (form.gestante == true) {
            binding.radioGestanteSim.isChecked = true
        } else {
            binding.radioGestanteNao.isChecked = true
        }

        if (form.alergiaCosmeticosMaquiagens == true) {
            binding.radioAlergiaCosmeticosSim.isChecked = true

            binding.etAlergiaCosmeticos.isVisible = true
            binding.etAlergiaCosmeticos.setText(form.alergiaCosmetico)
        } else {
            binding.radioAlergiaCosmeticosNao.isChecked = true
        }

        if (form.alergiaProdutosHigienePessoal == true) {
            binding.radioAlergiaHigieneSim.isChecked = true

            binding.etAlergiaHigiene.isVisible = true
            binding.etAlergiaHigiene.setText(form.alergiaProdutosHigiene)
        } else {
            binding.radioAlergiaHigieneNao.isChecked = true
        }

        if (form.fumante == true) {
            binding.radioFumaSim.isChecked = true
        } else {
            binding.radioFumaNao.isChecked = true
        }

        if (form.lacrimejaConstante == true) {
            binding.radioLacrimejaSim.isChecked = true
        } else {
            binding.radioLacrimejaNao.isChecked = true
        }

        if (form.sensibilidadeLuz == true) {
            binding.radioSensibilidadeLuzSim.isChecked = true
        } else {
            binding.radioSensibilidadeLuzNao.isChecked = true
        }

        if (form.fazTratamentoOcular == true) {
            binding.radioTratamentoOcularSim.isChecked = true

            binding.etTratamentoOcular.isVisible = true
            binding.etTratamentoOcular.setText(form.alergiaProdutosHigiene)
        } else {
            binding.radioTratamentoOcularNao.isChecked = true
        }

        if (form.fezCirurgiaOcularUltimos6Meses == true) {
            binding.radioCirurgiaOcularSim.isChecked = true
        } else {
            binding.radioCirurgiaOcularNao.isChecked = true
        }
    }
    //endregion

    //region Métodos Gerais

    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(requireContext()).setView(
                LayoutInflater.from(requireContext()).inflate(R.layout.dialog_loading, null)
            ).create()

            loadingDialog?.setCancelable(false)
        }

        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun makeForm(): AnamnesisForm {
        return AnamnesisForm(
            id = id,
            nome = binding.etNome.text.toString(),
            dataNascimento = binding.etDataNascimento.text.toString(),
            profissao = binding.etProfissao.text.toString(),
            celular = binding.etCelular.text.toString(),
            email = binding.etEmail.text.toString(),
            blefarite = binding.checkboxBlefarite.isChecked,
            tercolCalazio = binding.checkboxTercolCalazio.isChecked,
            conjutivite = binding.checkboxConjutivite.isChecked,
            diabetes = binding.checkboxDiabetes.isChecked,
            glaucoma = binding.checkboxGlaucoma.isChecked,
            fioAFio = binding.checkboxFioAFio.isChecked,
            volumeRusso = binding.checkboxVolumeRusso.isChecked,
            hibrido = binding.checkboxHibrido.isChecked,
            megaVolume = binding.checkboxMegaVolume.isChecked,
            volumeBrasileiro = binding.checkboxVolumeBrasileiro.isChecked,
            express = binding.checkboxExpress.isChecked,
            usaLentesContato = checkLentesContatoRg(),
            gestante = checkGestanteRg(),
            fumante = checkFumanteRg(),
            lacrimejaConstante = checkLacrimejoRg(),
            sensibilidadeLuz = checkSensibilidadeLuzRg(),
            fazTratamentoOcular = checkTratamentoOcularRg(),
            fezCirurgiaOcularUltimos6Meses = checkCirurgiaRecenteRg(),
            alergiaCosmeticosMaquiagens = checkAlergiaCosmeticosRg(),
            alergiaProdutosHigienePessoal = checkAlergiaProdutosHigieneRg(),
            alergiaCosmetico = binding.etAlergiaCosmeticos.text.toString(),
            alergiaProdutosHigiene = binding.etAlergiaHigiene.text.toString(),
            tratamentoOcular = binding.etTratamentoOcular.text.toString()
        )
    }

    private fun checkLentesContatoRg(): Boolean? {
        return when (binding.rgLentesContato.checkedRadioButtonId) {
            R.id.radio_usa_lentes_sim -> true
            R.id.radio_usa_lentes_nao -> false
            else -> null
        }
    }

    private fun checkGestanteRg(): Boolean? {
        return when (binding.rgGestante.checkedRadioButtonId) {
            R.id.radio_gestante_sim -> true
            R.id.radio_gestante_nao -> false
            else -> null
        }
    }

    private fun checkFumanteRg(): Boolean? {
        return when (binding.rgFumo.checkedRadioButtonId) {
            R.id.radio_fuma_sim -> true
            R.id.radio_fuma_nao -> false
            else -> null
        }
    }

    private fun checkLacrimejoRg(): Boolean? {
        return when (binding.rgLacrimejo.checkedRadioButtonId) {
            R.id.radio_lacrimeja_sim -> true
            R.id.radio_lacrimeja_nao -> false
            else -> null
        }
    }

    private fun checkSensibilidadeLuzRg(): Boolean? {
        return when (binding.rgSensibilidadeLuz.checkedRadioButtonId) {
            R.id.radio_sensibilidade_luz_sim -> true
            R.id.radio_sensibilidade_luz_nao -> false
            else -> null
        }
    }

    private fun checkTratamentoOcularRg(): Boolean? {
        return when (binding.rgTratamentoOcular.checkedRadioButtonId) {
            R.id.radio_tratamento_ocular_sim -> true
            R.id.radio_tratamento_ocular_nao -> false
            else -> null
        }
    }

    private fun checkCirurgiaRecenteRg(): Boolean? {
        return when (binding.rgCirurgiaRecente.checkedRadioButtonId) {
            R.id.radio_cirurgia_ocular_sim -> true
            R.id.radio_cirurgia_ocular_nao -> false
            else -> null
        }
    }

    private fun checkAlergiaCosmeticosRg(): Boolean? {
        return when (binding.rgAlergiaCosmeticos.checkedRadioButtonId) {
            R.id.radio_alergia_cosmeticos_sim -> true
            R.id.radio_alergia_cosmeticos_nao -> false
            else -> null
        }
    }

    private fun checkAlergiaProdutosHigieneRg(): Boolean? {
        return when (binding.rgAlergiaHigiene.checkedRadioButtonId) {
            R.id.radio_alergia_higiene_sim -> true
            R.id.radio_alergia_higiene_nao -> false
            else -> null
        }
    }
    //endregion
}
