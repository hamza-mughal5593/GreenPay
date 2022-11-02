package com.machineries_pk.mrk.activities.Module2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "10"
    }
    val text: LiveData<String> = _text



    fun updateddata(count: Int) {
        _text.value = count.toString()
    }


}