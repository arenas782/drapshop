@file:Suppress("UNCHECKED_CAST")

package com.drapshop.viewModel

/*
 Created by arenas on 10/05/21.
*/

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.drapshop.ui.login.LoginViewModel
import com.drapshop.ui.login.SignUpViewModel


class BaseViewModelFactory( vararg params: Any) : NewInstanceFactory() {
    private val mParams: Array<Any> = params as Array<Any>
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SignUpViewModel::class.java -> {
                SignUpViewModel() as T
            }

            LoginViewModel::class.java -> {
                LoginViewModel() as T
            }
            else -> {
                super.create(modelClass)
            }
        }
    }

}
