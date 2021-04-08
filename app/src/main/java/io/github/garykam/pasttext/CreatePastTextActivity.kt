package io.github.garykam.pasttext

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import io.github.garykam.pasttext.databinding.ActivityCreatePastTextBinding

class CreatePastTextActivity : Activity() {
    private lateinit var binding: ActivityCreatePastTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePastTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textFieldTimeInterval.setText("Day", false)
        binding.textFieldTimeInterval.setAdapter(
            ArrayAdapter(
                this, R.layout.item_time_interval,
                listOf("Day", "Month", "Year")
            )
        )
    }
}