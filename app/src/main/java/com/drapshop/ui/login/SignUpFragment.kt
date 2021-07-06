package com.drapshop.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.drapshop.R
import com.drapshop.databinding.FragmentSignUpBinding
import com.drapshop.utils.Utilities

import com.drapshop.viewModel.BaseViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private val TAG = "SignUpFragment"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, BaseViewModelFactory()).get(
            SignUpViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up, container, false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this




        binding.signUpButton.setOnClickListener {

            var auth: FirebaseAuth = Firebase.auth

            binding.progressBar.visibility = View.VISIBLE
            binding.signUpButton.visibility = View.INVISIBLE

            auth.createUserWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        closeSignUp()
                    } else {
                        task.exception.let {
                            if (it != null) {
                                Utilities.showSnackbar(requireActivity(),""+it.localizedMessage)
                            }
                        }
                    }
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.signUpButton.visibility = View.VISIBLE
                }
        }
        return binding.root
    }



    private fun closeSignUp(){
        Utilities.showSnackbar(requireActivity(),"You can login now")
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_main, LoginFragment())
        transaction.commit()
    }

}