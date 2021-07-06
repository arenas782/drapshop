package com.drapshop.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.drapshop.LoginActivity
import com.drapshop.MainActivity
import com.drapshop.R
import com.drapshop.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(){

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile, container, false
        )
        binding.logoutButton.setOnClickListener {
            logOut()
        }

        return binding.root
    }

    private fun logOut() {
        val auth: FirebaseAuth = Firebase.auth
        auth.signOut()

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        with (sharedPref!!.edit()){
            putBoolean("login",false)
            commit()
        }

        openLogin()
        requireActivity().finish()
    }

    private fun openLogin(){
        val intent = Intent (requireActivity(), LoginActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

}