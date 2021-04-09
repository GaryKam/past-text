package io.github.garykam.pasttext

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.github.garykam.pasttext.databinding.ActivityCreatePastTextBinding

class CreatePastTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePastTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePastTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textFieldTimeInterval.setText(R.string.day)
        binding.textFieldTimeInterval.setAdapter(
            ArrayAdapter(
                this, R.layout.item_time_interval,
                listOf(R.string.day, R.string.month, R.string.year)
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_option_done -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Confirmation")
                    .setMessage("Do you want to save the Past Text?")
                    .setNegativeButton("No") { dialog: DialogInterface, button: Int ->

                    }
                    .setPositiveButton("Yes") { dialog: DialogInterface, button: Int ->

                    }
                    .show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}