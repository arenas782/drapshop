package com.drapshop.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object FirestoreHelper {
    private const val TAG = "FirestoreHelper"
    private var auth: FirebaseAuth = Firebase.auth

    fun signUp(email: String, password: String): MutableLiveData<Boolean> {

        val signUpSuccess = MutableLiveData<Boolean>()


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signUpSuccess.value = true
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    signUpSuccess.value = false
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

                }
            }
        return signUpSuccess
    }

    fun logIn(email : String, password : String):MutableLiveData<Boolean>{
        val loginSuccess = MutableLiveData<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginSuccess.value = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    loginSuccess.value = false
                }
            }
        return loginSuccess
    }
}