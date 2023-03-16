package com.yjh.smarttracking.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yjh.smarttracking.MainActivity
import com.yjh.smarttracking.databinding.FragmentAddScopeBinding
import com.yjh.smarttracking.databinding.FragmentHomeBinding

class AddScopeFragment : Fragment() {

    private var _binding: FragmentAddScopeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[AddScopeViewModel::class.java]

        _binding = FragmentAddScopeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}