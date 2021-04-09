package io.github.garykam.pasttext

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.github.garykam.pasttext.databinding.ActivityCreatePastTextBinding
import java.text.DateFormat
import java.util.*

class CreatePastTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePastTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePastTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the options available for the text field.
        binding.textFieldTimeInterval.apply {
            setText(R.string.day)
            setAdapter(
                ArrayAdapter(
                    this@CreatePastTextActivity, R.layout.item_time_interval,
                    listOf(
                        getString(R.string.day),
                        getString(R.string.month),
                        getString(R.string.year)
                    )
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Create a menu with a "Done" option.
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_option_done -> {
                // Display an error if the Past Text title is empty.
                if (binding.editTextTitle.text.toString().isEmpty()) {
                    binding.editTextTitle.apply {
                        requestFocus()
                        error = "Title is required"
                    }
                    return super.onOptionsItemSelected(item)
                }

                // Display an error if the Past Text content is empty.
                if (binding.editTextContent.text.toString().isEmpty()) {
                    binding.editTextContent.apply {
                        requestFocus()
                        error = "Content is required"
                    }
                    return super.onOptionsItemSelected(item)
                }

                // Display an error if the Past Text duration is empty.
                val duration = binding.editTextDuration.text.toString()
                if (duration.isEmpty() || duration == "0") {
                    binding.editTextDuration.apply {
                        requestFocus()
                        error = "Duration is required"
                    }
                    return super.onOptionsItemSelected(item)
                }

                // Get the corresponding time interval.
                val field = when (binding.textFieldTimeInterval.text.toString()) {
                    getString(R.string.day) -> Calendar.DAY_OF_MONTH
                    getString(R.string.month) -> Calendar.MONTH
                    getString(R.string.year) -> Calendar.YEAR
                    else -> return super.onOptionsItemSelected(item)
                }

                // Calculate the date when the Past Text will be unlocked.
                val unlockDate = Calendar.getInstance()
                unlockDate.add(field, binding.editTextDuration.text.toString().toInt())

                // Display a confirmation dialog to lock and save the Past Text.
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.confirm)
                    .setMessage(
                        getString(
                            R.string.confirm_save,
                            DateFormat.getDateInstance().format(unlockDate.time)
                        )
                    )
                    .setNegativeButton(R.string.no) { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.yes) { _: DialogInterface, _: Int ->
                        // Store the Past Text data into an intent, and go back to MainActivity.
                        val intent = MainActivity.newIntent(
                            this, binding.editTextTitle.text.toString(),
                            binding.editTextContent.text.toString(),
                            unlockDate.time
                        )
                        startActivity(intent)
                    }
                    .show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}