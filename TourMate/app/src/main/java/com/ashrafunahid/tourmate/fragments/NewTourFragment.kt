package com.ashrafunahid.tourmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.databinding.FragmentNewTourBinding
import com.ashrafunahid.tourmate.models.TourModel
import com.ashrafunahid.tourmate.viewmodels.LoginViewModel
import com.ashrafunahid.tourmate.viewmodels.TourViewModel

class NewTourFragment : Fragment() {
    private lateinit var binding: FragmentNewTourBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val tourViewModel: TourViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTourBinding.inflate(inflater, container, false)

        binding.createTourBtn.setOnClickListener {
            if (binding.titleET.text.isNullOrEmpty() || binding.destinationET.text.isNullOrEmpty() || binding.budgetET.text.isNullOrEmpty()) {
                binding.titleET.error = "Title is required"
                binding.destinationET.error = "Destination is required"
                binding.budgetET.error = "Budget is required"
            } else {
                val title = binding.titleET.text.toString()
                val destination = binding.destinationET.text.toString()
                val budget = binding.budgetET.text.toString().toInt()
                val tourModel = TourModel(
                    userId = loginViewModel.user?.uid,
                    title = title,
                    destination = destination,
                    budget = budget,
                )
                tourViewModel.addTour(tourModel)
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

}