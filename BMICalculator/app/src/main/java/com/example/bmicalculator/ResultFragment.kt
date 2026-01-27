package com.example.bmicalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bmicalculator.ViewModels.BmiViewModels
import com.example.bmicalculator.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var bmiModel: BmiViewModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bmiModel = ViewModelProvider(requireActivity())[BmiViewModels::class]

        binding = FragmentResultBinding.inflate(inflater, container, false)

        // Receiving from Safe Arguments
        val msg = ResultFragmentArgs.fromBundle(arguments!!).score.toString()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

        bmiModel.bmi.observe(viewLifecycleOwner) { bmi ->
            binding.bmiScore.text = String.format("%.2f", bmi)
        }
        bmiModel.category.observe(viewLifecycleOwner) { category ->
            binding.category.text = category
        }

        return binding.root
    }
}