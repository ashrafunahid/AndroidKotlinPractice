package com.ashrafunahid.tourmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.databinding.FragmentLoginBinding
import com.ashrafunahid.tourmate.viewmodels.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        loginViewModel.authStatusLiveData.observe(viewLifecycleOwner, {authStatus ->
            if (authStatus == LoginViewModel.AuthenticationStatus.AUTHENTICATED) {
                findNavController().navigate(R.id.login_to_tour_action)
            }
        })

        loginViewModel.errMsgLiveData.observe(viewLifecycleOwner, { errMsg ->
            binding.errMsgTV.text = errMsg
        })

        binding.loginBtn.setOnClickListener {
            loginViewModel.login(binding.emailET.text.toString(), binding.passET.text.toString())
        }

        binding.registerBtn.setOnClickListener {
            loginViewModel.register(binding.emailET.text.toString(), binding.passET.text.toString())
        }

        return binding.root
    }

}