package com.yjh.smarttracking.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddScopeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is add scope Fragment"
    }
    val text: LiveData<String> = _text
}