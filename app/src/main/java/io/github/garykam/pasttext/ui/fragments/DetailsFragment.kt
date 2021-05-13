package io.github.garykam.pasttext.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.garykam.pasttext.R
import io.github.garykam.pasttext.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)

        binding.textTitle.text = args.title
        binding.textContent.text = args.content
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
