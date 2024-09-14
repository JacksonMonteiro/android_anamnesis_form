package com.example.anamnesisform.presentation

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.example.anamnesisform.R
import com.example.anamnesisform.commons.UiState
import com.example.anamnesisform.databinding.ActivityMainBinding
import com.example.anamnesisform.domain.model.AnamnesisForm
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainActivityViewModel by inject()

    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupListeners()
        setupObservers()
    }

    //region SETUPS
    private fun setupView() {
        disableDarkMode()
    }

    private fun setupListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.validate(makeForm())
        }

        binding.rgAlergiaCosmeticos.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.radio_alergia_cosmeticos_sim -> {
                    binding.etAlergiaCosmeticos.isVisible = true
                }

                else -> {
                    binding.etAlergiaCosmeticos.isVisible = false
                }
            }
        }

        binding.rgAlergiaHigiene.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.radio_alergia_higiene_sim -> {
                    binding.etAlergiaHigiene.isVisible = true
                }

                else -> {
                    binding.etAlergiaHigiene.isVisible = false
                }
            }
        }

        binding.rgTratamentoOcular.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.radio_tratamento_ocular_sim -> {
                    binding.etTratamentoOcular.isVisible = true
                }

                else -> {
                    binding.etTratamentoOcular.isVisible = false
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.formError.observe(this) { error ->
            showSnackbar(error)
        }

        viewModel.response.observe(this) { state ->
            when (state) {
                is UiState.Success -> handleSuccess()
                is UiState.Error -> showSnackbar(state.message)
                is UiState.Loading -> handleLoading(state.isLoading)
            }
        }
    }
    //endregion

    //region Handlers
    private fun handleSuccess() {
        AlertDialog.Builder(this).setTitle("SUCESSO!")
            .setMessage("Os dados da sua cliente foram salvos com sucesso")
            .setPositiveButton("Voltar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            }).create().show()
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else hideLoadingDialog()
    }
    //endregion

    //region Métodos Gerais
    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(this).setView(
                layoutInflater.inflate(R.layout.dialog_loading, null)
            ).create()

            loadingDialog?.setCancelable(false)
        }

        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun makeForm(): AnamnesisForm {
        return AnamnesisForm(
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