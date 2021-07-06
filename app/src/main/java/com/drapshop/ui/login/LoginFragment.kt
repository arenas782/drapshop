package com.drapshop.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.drapshop.MainActivity
import com.drapshop.R
import com.drapshop.databinding.FragmentLoginBinding
import com.drapshop.utils.Utilities
import com.drapshop.viewModel.BaseViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val TAG = "LoginFragment"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, BaseViewModelFactory()).get(
            LoginViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.signUpTv.setOnClickListener {
            openSignUp()
        }

        binding.loginButton.setOnClickListener {
            login()
        }
        return binding.root
    }

    private fun login() {
        val auth: FirebaseAuth = Firebase.auth

        binding.progressBar.visibility = View.VISIBLE
        binding.loginButton.visibility = View.INVISIBLE
        binding.signUpTv.visibility = View.INVISIBLE

        auth.signInWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    closeLogin()
                } else {
                    task.exception.let {
                        if (it != null) {
                            Utilities.showSnackbar(requireActivity(),""+it.localizedMessage)
                        }
                    }
                }
                binding.progressBar.visibility = View.INVISIBLE
                binding.loginButton.visibility = View.VISIBLE
                binding.signUpTv.visibility = View.VISIBLE
            }
    }

    private fun closeLogin() {
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        with (sharedPref!!.edit()){
            putBoolean("login",true)
            commit()
        }

        val intent = Intent (requireActivity(), MainActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    private fun openSignUp(){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_main, SignUpFragment()).addToBackStack(null)
        transaction.commit()
    }

}