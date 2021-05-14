package io.github.garykam.pasttext.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import io.github.garykam.pasttext.PastTextApplication
import io.github.garykam.pasttext.R
import io.github.garykam.pasttext.databinding.FragmentListBinding
import io.github.garykam.pasttext.ui.MainViewModel
import io.github.garykam.pasttext.ui.MainViewModelFactory
import io.github.garykam.pasttext.ui.adapter.PastTextListAdapter

class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as PastTextApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        viewModel.pastTexts.observe(viewLifecycleOwner) { pastTexts ->
            binding.recyclerPastText.adapter = PastTextListAdapter(
                pastTexts,
                PastTextListAdapter.PastTextListener { index ->
                    val pastText = pastTexts[index]

                    if (pastText.isUnlocked()) {
                        // Display details of the clicked Past Text.
                        ListFragmentDirections.actionListFragmentToDetailsFragment(
                            pastText.title,
                            pastText.content
                        ).let { action ->
                            findNavController().navigate(action)
                        }
                    } else {
                        // Alert the user that the Past Text is still locked.
                        AlertDialog.Builder(requireContext())
                            .setMessage(R.string.past_text_locked)
                            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                            .show()
                    }
                }
            )
        }

        // Floating action button to navigate to the Past Text creation screen.
        binding.floatingButtonCreate.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_createFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Create a menu with a "Delete All" option.
        inflater.inflate(R.menu.list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_option_delete -> {
                deletePastTexts()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Removes all Past Texts.
     */
    private fun deletePastTexts() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.confirm)
            .setMessage(R.string.confirm_delete)
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.deletePastTexts()
                dialog.dismiss()
            }
            .show()
    }
}
