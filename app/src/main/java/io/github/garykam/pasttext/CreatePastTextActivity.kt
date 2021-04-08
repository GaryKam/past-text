package io.github.garykam.pasttext

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import io.github.garykam.pasttext.databinding.ActivityCreatePastTextBinding

class CreatePastTextActivity : Activity() {
    private lateinit var binding: ActivityCreatePastTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePastTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (binding.editTextTitle.requestFocus()) {
            inputManager.showSoftInput(binding.editTextTitle, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.editTextTitle.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                inputManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}