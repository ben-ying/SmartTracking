package com.mobileappt20.ui.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mobileappt20.R
import com.mobileappt20.data.Scope.Companion.NAME
import com.mobileappt20.data.Scope.Companion.SCHEDULE_TIME
import com.mobileappt20.databinding.FragmentOverviewScopeBinding
import kotlinx.coroutines.launch
import java.util.*

class OverviewScopeFragment : Fragment() {

    private var _binding: FragmentOverviewScopeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: OverviewScopeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.retrieveScopeUiState.collect {
                    binding.progressLayout.progressBar.isVisible = false
                    if (it.action == Action.RETRIEVE_SCOPE_SUCCESS) {
                        Log.d("test", "documents: ${it.documents}")
                        if (it.documents.size == 1) {
                            binding.scopeLayout.scopeTitle.text =
                                it.documents[0].get(NAME).toString()
                            binding.scopeLayout.scopeDatetime.text =
                                it.documents[0].get(SCHEDULE_TIME).toString()
                            binding.scopeLayout.scopeDatetime.isVisible = true
                            binding.scopeLayout.prevBtn.isVisible = false
                            binding.scopeLayout.nextBtn.isVisible = false
                        } else if (it.documents.size > 1) {
                            binding.scopeLayout.scopeTitle.text =
                                it.documents[0].get(NAME).toString()
                            binding.scopeLayout.scopeDatetime.text =
                                it.documents[0].get(SCHEDULE_TIME).toString()
                            binding.scopeLayout.scopeDatetime.isVisible = true
                            binding.scopeLayout.prevBtn.isVisible = true
                            binding.scopeLayout.nextBtn.isVisible = true
                        } else {
                            binding.scopeLayout.scopeTitle.setText(R.string.no_scope)
                            binding.scopeLayout.scopeDatetime.isVisible = false
                            binding.scopeLayout.prevBtn.isVisible = false
                            binding.scopeLayout.nextBtn.isVisible = false
                        }
                    } else if (it.action == Action.RETRIEVE_SCOPE_FAILED) {
                        Toast.makeText(
                            requireContext(),
                            R.string.retrieve_scope_failed,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewScopeBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addScopeFragment)
        }

        binding.progressLayout.progressBar.isVisible = true
        val y = Calendar.getInstance().get(Calendar.YEAR)
        val m = Calendar.getInstance().get(Calendar.MONTH)
        val d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        viewModel.retrieveScope("scopes/$y/${m + 1}/$d/scope")
        val calendarView = binding.calendarLayout.calendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            binding.progressLayout.progressBar.isVisible = true
            viewModel.retrieveScope("scopes/$year/${month + 1}/$dayOfMonth/scope")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}