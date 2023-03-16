package com.mobileappt20.smarttracking.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OverviewScopeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is overview scope Fragment"
    }
    val text: LiveData<String> = _text
}