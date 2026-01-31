package com.ashrafunahid.tourmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.databinding.FragmentLandingBinding
import com.ashrafunahid.tourmate.viewmodels.LoginViewModel

class LandingFragment : Fragment() {
    private lateinit var binding: FragmentLandingBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(inflater, container, false)

        loginViewModel.authStatusLiveData.observe(viewLifecycleOwner, { it ->
            if (it == LoginViewModel.AuthenticationStatus.AUTHENTICATED) {
                findNavController().navigate(R.id.tour_list_action)
            } else {
                findNavController().navigate(R.id.login_action)
            }
        })

        return binding.root
    }

}