package io.github.garykam.pasttext.ui

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
import io.github.garykam.pasttext.R
import io.github.garykam.pasttext.databinding.FragmentCreateBinding
import io.github.garykam.pasttext.model.PastText
import io.github.garykam.pasttext.viewmodel.MainViewModel
import java.text.DateFormat
import java.util.*

class CreateFragment : Fragment(R.layout.fragment_create) {
    private var _binding: FragmentCreateBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateBinding.bind(view)

        // Set the options available for the text field.
        binding.textFieldTimeInterval.apply {
            setText(R.string.day)
            setAdapter(
                ArrayAdapter(
                    requireContext(), R.layout.item_time_interval,
                    listOf(
                        getString(R.string.day),
                        getString(R.string.month),
                        getString(R.string.year)
                    )
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Create a menu with a "Done" option.
        inflater.inflate(R.menu.create, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_option_done ->
                submitPastText()
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Stores the newly created Past Text.
     *
     * @return False if the Past Text is not valid
     */
    private fun submitPastText(): Boolean {
        // Display an error if the Past Text title is empty.
        if (binding.editTextTitle.text.toString().isEmpty()) {
            binding.editTextTitle.apply {
                requestFocus()
                error = "Title is required"
            }

            return false
        }

        // Display an error if the Past Text content is empty.
        if (binding.editTextContent.text.toString().isEmpty()) {
            binding.editTextContent.apply {
                requestFocus()
                error = "Content is required"
            }

            return false
        }

        // Display an error if the Past Text duration is empty.
        val duration = binding.editTextDuration.text.toString()
        if (duration.isEmpty() || duration == "0") {
            binding.editTextDuration.apply {
                requestFocus()
                error = "Duration is required"
            }

            return false
        }

        // Get the corresponding time interval.
        val field = when (binding.textFieldTimeInterval.text.toString()) {
            getString(R.string.day) -> Calendar.DAY_OF_MONTH
            getString(R.string.month) -> Calendar.MONTH
            getString(R.string.year) -> Calendar.YEAR
            else -> return false
        }

        // Calculate the date when the Past Text will be unlocked.
        val unlockDate = Calendar.getInstance()
        unlockDate.add(field, binding.editTextDuration.text.toString().toInt())

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
                viewModel.addPastText(
                    PastText(
                        binding.editTextTitle.text.toString(),
                        binding.editTextContent.text.toString(),
                        unlockDate.time
                    )
                )

                // Return to the previous fragment.
                findNavController().navigate(R.id.action_createFragment_to_listFragment)
            }
            .show()

        return true
    }
}