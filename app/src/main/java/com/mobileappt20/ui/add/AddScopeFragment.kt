package com.mobileappt20.ui.add

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
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
import com.mobileappt20.R
import com.mobileappt20.databinding.FragmentAddScopeBinding
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*


class AddScopeFragment : Fragment() {

    private var _binding: FragmentAddScopeBinding? = null

    private val binding get() = _binding!!
    private var scopeIndex = 0
    private var scopeName = String()
    private val viewModel: AddScopeViewModel by viewModels()
    private var year = Calendar.getInstance().get(Calendar.YEAR)
    private var month = Calendar.getInstance().get(Calendar.MONTH) + 1
    private var dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    private var hour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
    private var minute = Calendar.getInstance()[Calendar.MINUTE]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.scopeIndexUiState.collect {
                    scopeIndex = it.scopeIndex
                    displayScope()
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createScopeUiState.collect {
                    if (it.action != Action.NONE) {
                        binding.progressLayout.progressBar.isVisible = false
                    }
                    if (it.action == Action.CREATE_SCOPE_SUCCESS) {
                        Toast.makeText(
                            requireContext(),
                            R.string.scope_created_successfully,
                            Toast.LENGTH_LONG
                        ).show()
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    } else if (it.action == Action.CREATE_SCOPE_FAILED) {
                        Toast.makeText(
                            requireContext(),
                            R.string.scope_created_failed,
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
        _binding = FragmentAddScopeBinding.inflate(inflater, container, false)
        val time: String = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
        binding.calendarLayout.timeText.isVisible = true
        binding.calendarLayout.timeText.text = time
        binding.calendarLayout.timeText.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    hour = selectedHour
                    minute = selectedMinute
                    val calendar = Calendar.getInstance()
                    calendar[Calendar.HOUR_OF_DAY] = hour
                    calendar[Calendar.MINUTE] = minute
                    binding.calendarLayout.timeText.text =
                        DateFormat.getTimeInstance(DateFormat.SHORT)
                            .format(Date(calendar.timeInMillis))
                }, hour, minute, is24HourFormat(requireContext())
            )
            timePickerDialog.setTitle(R.string.select_time)
            timePickerDialog.show()
        }
        binding.scopeLayout.prevBtn.isVisible = true
        binding.scopeLayout.nextBtn.isVisible = true
        binding.scopeLayout.prevBtn.setOnClickListener {
            viewModel.setScopeIndex(scopeIndex, -1)
        }
        binding.scopeLayout.nextBtn.setOnClickListener {
            viewModel.setScopeIndex(scopeIndex, 1)
        }
        val calendarView = binding.calendarLayout.calendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            this.year = year
            this.month = month
            this.dayOfMonth = dayOfMonth
        }
        binding.addScopeBtn.setOnClickListener {
            binding.progressLayout.progressBar.isVisible = true
            val selectedCalendar: Calendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth, hour, minute)
            viewModel.createScope(
                scopeName,
                Calendar.getInstance().time.time,
                selectedCalendar.timeInMillis,
                "scopes/$year/${month + 1}/$dayOfMonth/scope"
            )
        }

        return binding.root
    }

    private fun displayScope() {
        when (scopeIndex) {
            0 -> {
                scopeName = getString(R.string.scope_a)
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
            }
            1 -> {
                scopeName = getString(R.string.scope_b)
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
            }
            2 -> {
                scopeName = getString(R.string.scope_c)
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
            }
            3 -> {
                scopeName = getString(R.string.scope_d)
                binding.scopeLayout.scopeImage.setImageResource(R.mipmap.scope_img)
            }
        }
        binding.scopeLayout.scopeTitle.text = scopeName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}