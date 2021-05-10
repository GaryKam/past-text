package io.github.garykam.pasttext.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import io.github.garykam.pasttext.viewmodel.MainViewModel
import io.github.garykam.pasttext.PastTextListAdapter
import io.github.garykam.pasttext.R
import io.github.garykam.pasttext.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        binding.recyclerPastText.adapter =
            PastTextListAdapter(requireContext(), viewModel.pastTexts.value!!)

        // Floating action button to navigate to the Past Text creation screen.
        binding.floatingButtonCreate.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_createFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}