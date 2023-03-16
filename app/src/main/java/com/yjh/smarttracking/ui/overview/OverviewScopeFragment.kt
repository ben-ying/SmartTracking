package com.yjh.smarttracking.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yjh.smarttracking.R
import com.yjh.smarttracking.databinding.FragmentOverviewScopeBinding

class OverviewScopeFragment : Fragment() {

    private var _binding: FragmentOverviewScopeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: OverviewScopeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewScopeBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addScopeFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}