package com.example.bmicalculator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bmicalculator.ViewModels.BmiViewModels
import com.example.bmicalculator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bmiModel: BmiViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bmiModel = ViewModelProvider(requireActivity())[BmiViewModels::class]
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.calculateButton.setOnClickListener { v ->
            val weight: Double = binding.weightInput.text.toString().toDouble()
            val height: Double = binding.heightInput.text.toString().toDouble()

            bmiModel.CalculateBMI(weight, height)

            // Passing data using Safe Arguments
            val action = HomeFragmentDirections.resultAction()
            action.score = "This is data passing using safe args"

            findNavController().navigate(action)
        }

        return binding.root
    }

}