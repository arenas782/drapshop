package com.drapshop.ui.login



import android.util.Patterns
import androidx.lifecycle.*
import com.drapshop.utils.FirestoreHelper
import com.drapshop.utils.Resource
import kotlinx.coroutines.Dispatchers


class SignUpViewModel (): ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData("")
    val passwordConfirmation = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()




    val errorEmail = MutableLiveData("")
    val errorPassword = MutableLiveData("")
    val errorPasswordConfirmation = MutableLiveData("")






    private fun isEmailValid(it: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    private fun isPasswordValid(it: String): Boolean {
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@.*#\$%!\\-_?&])(?=\\S+\$).{6,}".toRegex()
        return it.matches(regex = regex)
    }
    private fun passwordsMatches(pass: String,passConfirm: String): Boolean {

        return pass == passConfirm
    }





    val valid = MediatorLiveData<Boolean>().apply {



        var emailValid: Boolean? = null
        var passwordValid: Boolean? = null
        var passwordConfirmationValid: Boolean? = null


        fun update() {
            val localEmailValid = emailValid
            val localPasswordValid = passwordValid
            val localPasswordConfirmationValid = passwordConfirmationValid

            if (emailValid != null && passwordValid != null && passwordConfirmationValid != null)
                this.value =
                    localEmailValid == true && localPasswordValid == true && localPasswordConfirmationValid == true
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
                if (passwordConfirmation.value.isNullOrEmpty()){
                    errorPassword.value = ""
                }
                else {
                    val bothValids = passwordsMatches(password.value.toString(),passwordConfirmation.value.toString())
                    if (bothValids){
                        errorPassword.value = ""
                    }else{
                        errorPassword.value = "Las contraseñas no coinciden"
                        passwordValid=false
                    }
                }
            } else {
                errorPassword.value = "Tu contraseña no cumple los criterios"
            }
            update()
        }
        addSource(passwordConfirmation) {
            passwordConfirmationValid = passwordsMatches(password.value.toString(),passwordConfirmation.value.toString())

            if (passwordConfirmationValid == true){
                errorPasswordConfirmation.value = ""
            } else {
                errorPasswordConfirmation.value = "Las contraseñas no coinciden"
            }
            update()
        }
    }
}