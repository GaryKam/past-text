package io.github.garykam.pasttext.ui

import androidx.lifecycle.*
import io.github.garykam.pasttext.data.model.PastText
import io.github.garykam.pasttext.data.repository.PastTextRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: PastTextRepository
) : ViewModel() {
    val pastTexts: LiveData<List<PastText>> = repository.pastTexts.asLiveData()

    /**
     * Saves the Past Text.
     *
     * @return True if the Past Text was successfully added
     */
    fun addPastText(pastText: PastText): Boolean {
        // Prevent adding Past Text with same primary key.
        pastTexts.value!!.forEach {
            if (it.title == pastText.title) {
                return false
            }
        }

        viewModelScope.launch {
            repository.addPastText(pastText)
        }

        return true
    }

    fun deletePastTexts() {
        viewModelScope.launch {
            repository.deleteAllPastTexts()
        }
    }
}

class MainViewModelFactory(
    private val repository: PastTextRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
