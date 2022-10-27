package com.machineries_pk.mrk.activities.Module2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Module2.ui.home.HomeFragment
import com.machineries_pk.mrk.activities.Module2.ui.notifications.NotificationsFragment
import com.machineries_pk.mrk.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        loadFragment(HomeFragment())
        navView.menu.findItem(R.id.navigation_home).isChecked = true

        navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    loadFragment(NotificationsFragment())
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        })


    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_frame, fragment)
        //        transaction.addToBackStack(null);
        transaction.commit()
    }
}