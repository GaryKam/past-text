package io.github.garykam.pasttext.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import io.github.garykam.pasttext.PastTextApplication
import io.github.garykam.pasttext.R
import io.github.garykam.pasttext.data.model.PastText
import io.github.garykam.pasttext.data.service.AlarmHelper
import io.github.garykam.pasttext.databinding.FragmentCreateBinding
import io.github.garykam.pasttext.ui.MainViewModel
import io.github.garykam.pasttext.ui.MainViewModelFactory
import java.text.DateFormat
import java.util.*

class CreateFragment : Fragment(R.layout.fragment_create) {
    private var _binding: FragmentCreateBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as PastTextApplication).repository)
    }
    private lateinit var timeFields: Map<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        // Requires activity context to get String resources.
        timeFields = mapOf(
            getString(R.string.day) to Calendar.DAY_OF_MONTH,
            getString(R.string.month) to Calendar.MONTH,
            getString(R.string.year) to Calendar.YEAR
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateBinding.bind(view)

        // Set the options available for the text field.
        binding.textFieldTimeInterval.apply {
            setText(R.string.day)
            setAdapter(
                ArrayAdapter(
                    context,
                    R.layout.item_time_interval,
                    listOf(timeFields.keys)
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Create a menu with a "Done" option.
        inflater.inflate(R.menu.menu_create, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_option_done -> submitPastText()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Stores the newly created Past Text.
     *
     * @return True if the Past Text is submitted successfully
     */
    private fun submitPastText(): Boolean {
        // Display an error if the Past Text title is empty.
        if (binding.editTextTitle.text.toString().isBlank()) {
            binding.editTextTitle.apply {
                requestFocus()
                error = getString(R.string.error_title)
            }

            return false
        }

        // Display an error if the Past Text content is empty.
        if (binding.editTextContent.text.toString().isBlank()) {
            binding.editTextContent.apply {
                requestFocus()
                error = getString(R.string.error_content)
            }

            return false
        }

        // Display an error if the Past Text duration is empty.
        val duration = binding.editTextDuration.text.toString().toIntOrNull()
        if (duration == null) {
            binding.editTextDuration.apply {
                requestFocus()
                error = getString(R.string.error_duration)
            }

            return false
        }

        // Calculate the date when the Past Text will be unlocked.
        val unlockDate = Calendar.getInstance().apply {
            // Get the corresponding time interval.
            val field = timeFields[binding.textFieldTimeInterval.text.toString()] ?: return false

            add(field, duration)
        }.time

        // Display a confirmation dialog to lock and save the Past Text.
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.confirm)
            .setMessage(
                getString(
                    R.string.confirm_save,
                    DateFormat.getDateInstance().format(unlockDate.time)
                )
            )
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.yes) { _, _ ->
                // Save the Past Text into the view model.
                if (viewModel.addPastText(
                        PastText(
                            binding.editTextTitle.text.toString(),
                            binding.editTextContent.text.toString(),
                            unlockDate
                        )
                    )
                ) {
                    // Alert when the Past Text unlocks.
                    AlarmHelper.startAlarm(requireActivity().applicationContext, unlockDate)

                    // Return to the previous fragment.
                    findNavController().navigate(R.id.action_createFragment_to_listFragment)
                } else {
                    // Notify the user that saving the Past Text failed.
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.error)
                        .setMessage(R.string.past_text_duplicate)
                        .setNeutralButton(R.string.ok) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
            .show()

        return true
    }
}
