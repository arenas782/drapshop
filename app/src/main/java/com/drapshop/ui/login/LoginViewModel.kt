package com.drapshop.ui.login



import android.util.Patterns
import androidx.lifecycle.*
import com.drapshop.utils.FirestoreHelper
import com.drapshop.utils.Resource
import kotlinx.coroutines.Dispatchers


class LoginViewModel (): ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    val errorEmail = MutableLiveData("")
    val errorPassword = MutableLiveData("")


    private fun isEmailValid(it: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    private fun isPasswordValid(it: String): Boolean {
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@.*#\$%!\\-_?&])(?=\\S+\$).{6,}".toRegex()
        return it.matches(regex = regex)
    }



    fun login() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = FirestoreHelper.logIn(email.value!!, password.value!!).value))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.toString()))
        }
    }



    val valid = MediatorLiveData<Boolean>().apply {



        var emailValid: Boolean? = null
        var passwordValid: Boolean? = null


        fun update() {
            val localEmailValid = emailValid
            val localPasswordValid = passwordValid

            if (emailValid != null && passwordValid != null )
                this.value =
                    localEmailValid == true && localPasswordValid == true
        }

        addSource(email) {
            emailValid = isEmailValid(it)
            if (emailValid == true) {
                errorEmail.value = ""
            } else {
                errorEmail.value = "Formato de correo inválido"
            }
            update()
        }
        addSource(password) {
            passwordValid = isPasswordValid(it)
            if (passwordValid == true) {
                errorPassword.value = ""
            } else {
                errorPassword.value = "Tu contraseña no cumple los criterios"
            }
            update()
        }

    }
}