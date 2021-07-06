package com.drapshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.drapshop.ui.home.HomeFragment
import com.drapshop.ui.maps.MapsFragment
import com.drapshop.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val homeFragment = HomeFragment()
    private val mapsFragment = MapsFragment()
    private val profileFragment = ProfileFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager.beginTransaction().apply {
            add(R.id.nav_host_container, profileFragment,"asd").hide(profileFragment)
            add(R.id.nav_host_container, mapsFragment, getString(R.string.title_maps)).hide(mapsFragment)
            add(R.id.nav_host_container, homeFragment, getString(R.string.title_home))
        }.commit()


        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.menu_home->{
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.menu_maps -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(mapsFragment).commit()
                    activeFragment = mapsFragment
                    true
                }
                R.id.menu_profile -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                    activeFragment = profileFragment
                    true
                }
                else -> false
            }
        }
    }


}