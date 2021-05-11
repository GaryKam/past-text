package io.github.garykam.pasttext.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.garykam.pasttext.data.model.PastText

class MainViewModel : ViewModel() {
    private val _pastTexts: MutableLiveData<MutableList<PastText>> = MutableLiveData(mutableListOf())
    val pastTexts: LiveData<MutableList<PastText>> = _pastTexts

    fun addPastText(pastText: PastText) {
        _pastTexts.value!!.add(pastText)
    }
}