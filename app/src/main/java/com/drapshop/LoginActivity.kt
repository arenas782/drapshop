package com.drapshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.drapshop.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<LoginFragment>(R.id.fragment_main)
        }

        val sharedPref = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val loggedin = sharedPref.getBoolean("login",false)
        if(loggedin){
            goToMain()
        }
    }

    private fun goToMain() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager

        if (fragment.backStackEntryCount == 0) {
            // No backstack to pop, so calling super
            super.onBackPressed()
        } else {
            fragment.popBackStack()
        }
    }
}