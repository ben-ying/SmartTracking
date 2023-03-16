package com.yjh.smarttracking.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yjh.smarttracking.R
import com.yjh.smarttracking.databinding.FragmentAddScopeBinding

class AddScopeFragment : Fragment() {

    private var _binding: FragmentAddScopeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var scopeIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[AddScopeViewModel::class.java]

        _binding = FragmentAddScopeBinding.inflate(inflater, container, false)
        binding.scopeLayout.prevBtn.isVisible = true
        binding.scopeLayout.nextBtn.isVisible = true
        binding.scopeLayout.prevBtn.setOnClickListener {
            setScopeIndex(-1)
        }
        binding.scopeLayout.nextBtn.setOnClickListener {
            setScopeIndex(1)
        }

        displayScope()

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

    private fun setScopeIndex(delta: Int) {
        scopeIndex += delta
        if (scopeIndex > 3) {
            scopeIndex = 0
        } else if (scopeIndex < 0) {
            scopeIndex = 3
        }
        displayScope()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}