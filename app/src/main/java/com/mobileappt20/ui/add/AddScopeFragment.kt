package com.mobileappt20.ui.add

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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileappt20.R
import com.mobileappt20.databinding.FragmentAddScopeBinding
import kotlinx.coroutines.launch

class AddScopeFragment : Fragment() {

    private var _binding: FragmentAddScopeBinding? = null

    private val binding get() = _binding!!
    private var scopeIndex = 0
    private val viewModel: AddScopeViewModel by viewModels()
    private val firestore = Firebase.firestore

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
        binding.addScopeBtn.setOnClickListener {
            // Create a new user with a first and last name
            val user = hashMapOf(
                "first" to "Ada",
                "last" to "Lovelace",
                "born" to 1815
            )
            firestore.collection("scopes")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("AddScopeFragment", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("AddScopeFragment", "Error adding document", e)
                }
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