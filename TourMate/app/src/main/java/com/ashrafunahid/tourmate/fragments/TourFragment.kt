package com.ashrafunahid.tourmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.adapters.TourAdapter
import com.ashrafunahid.tourmate.databinding.FragmentTourBinding
import com.ashrafunahid.tourmate.utils.details_screen
import com.ashrafunahid.tourmate.utils.tour_status_update
import com.ashrafunahid.tourmate.viewmodels.LoginViewModel
import com.ashrafunahid.tourmate.viewmodels.TourViewModel

class TourFragment : Fragment() {
    private lateinit var binding: FragmentTourBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val tourViewModel: TourViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTourBinding.inflate(inflater, container, false)

        val adapter = TourAdapter { id, action, status ->
            when (action) {
                details_screen -> {
                    val tourId = id
                    val navAction = TourFragmentDirections.tourDetailsAction(tourId)
                    findNavController().navigate(navAction)
                }

                tour_status_update -> {
                    tourViewModel.updateTourStatus(id, status)
                }
            }

        }

        binding.tourRecyclerview.layoutManager = LinearLayoutManager(activity)
        binding.tourRecyclerview.adapter = adapter
        tourViewModel.getToursByUser(loginViewModel.user!!.uid)
            .observe(viewLifecycleOwner, { tours ->
                adapter.submitList(tours)
            })

        binding.newTourFab.setOnClickListener {
            findNavController().navigate(R.id.new_tour_action)
        }

        return binding.root
    }

}