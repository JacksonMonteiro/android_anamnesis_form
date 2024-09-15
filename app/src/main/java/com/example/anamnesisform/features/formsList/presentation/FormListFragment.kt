package com.example.anamnesisform.features.formsList.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anamnesisform.R
import com.example.anamnesisform.commons.interfaces.IOnClickListener
import com.example.anamnesisform.commons.ui.UiState
import com.example.anamnesisform.databinding.FragmentFormListBinding
import com.example.anamnesisform.domain.model.AnamnesisForm
import com.example.anamnesisform.features.formsList.presentation.adapter.FormListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class FormListFragment : Fragment() {
    private lateinit var binding: FragmentFormListBinding
    private val viewModel: FormListViewModel by inject()

    private var adapter: FormListAdapter? = null
    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getForms()
    }

    private fun setupObservers() {
        viewModel.response.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> handleSuccess(state.data)
                is UiState.Error -> showSnackbar(state.message)
                is UiState.Loading -> handleLoading(state.isLoading)
            }
        }
    }

    private fun handleSuccess(forms: List<AnamnesisForm>) {
        adapter = FormListAdapter(forms, object : IOnClickListener {
            override fun onClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("form", adapter?.getItem(position))
                findNavController().navigate(R.id.action_list_to_visualization, bundle)
            }
        })

        binding.rvForms.adapter = adapter
        binding.rvForms.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else hideLoadingDialog()
    }

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
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}