package com.mobileappt20.smarttracking.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mobileappt20.smarttracking.R
import com.mobileappt20.smarttracking.databinding.FragmentAddScopeBinding
import kotlinx.coroutines.launch

class AddScopeFragment : Fragment() {

    private var _binding: FragmentAddScopeBinding? = null

    private val binding get() = _binding!!
    private var scopeIndex = 0
    private val viewModel: AddScopeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    scopeIndex = it.scopeIndex
                    displayScope()
                    Log.d("AddScopeFragment", "scopeIndex: ${it.scopeIndex}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddScopeBinding.inflate(inflater, container, false)
        binding.scopeLayout.prevBtn.isVisible = true
        binding.scopeLayout.nextBtn.isVisible = true
        binding.scopeLayout.prevBtn.setOnClickListener {
            viewModel.setScopeIndex(scopeIndex, -1)
        }
        binding.scopeLayout.nextBtn.setOnClickListener {
            viewModel.setScopeIndex(scopeIndex, 1)
        }

        return binding.root
    }

    private fun displayScope() {
        when (scopeIndex) {
            0 -> {
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
                binding.scopeLayout.scopeTitle.setText(R.string.scope_a)
            }
            1 -> {
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
                binding.scopeLayout.scopeTitle.setText(R.string.scope_b)
            }
            2 -> {
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
                binding.scopeLayout.scopeTitle.setText(R.string.scope_c)
            }
            3 -> {
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
                binding.scopeLayout.scopeTitle.setText(R.string.scope_d)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}