package com.yjh.smarttracking.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yjh.smarttracking.R
import com.yjh.smarttracking.databinding.FragmentOverviewScopeBinding

class OverviewScopeFragment : Fragment() {

    private var _binding: FragmentOverviewScopeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val overviewScopeViewModel =
            ViewModelProvider(this)[OverviewScopeViewModel::class.java]
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