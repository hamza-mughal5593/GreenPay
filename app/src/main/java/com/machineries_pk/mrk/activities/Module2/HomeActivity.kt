package com.machineries_pk.mrk.activities.Module2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Module2.ui.home.HomeFragment
import com.machineries_pk.mrk.activities.Module2.ui.home.HomeViewModel
import com.machineries_pk.mrk.activities.Module2.ui.notifications.NotificationsFragment
import com.machineries_pk.mrk.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var notificationsViewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationsViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions( arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }


        loadFragment(HomeFragment(), HomeFragment::class.java.simpleName)
        navView.menu.findItem(R.id.navigation_home).isChecked = true

        navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment(),HomeFragment::class.java.simpleName)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    loadFragment(NotificationsFragment(),NotificationsFragment::class.java.simpleName)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        })


    }

//    private fun loadFragment(fragment: Fragment) {
//        // load fragment
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_frame, fragment)
//        //        transaction.addToBackStack(null);
//        transaction.commit()
//    }
    fun loadFragment(fragment: Fragment?, tagFragmentName: String?) {
        val mFragmentManager = supportFragmentManager
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val currentFragment = mFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }
        var fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName)
        if (fragmentTemp == null) {
            fragmentTemp = fragment
            fragmentTransaction.add(R.id.nav_host_frame, fragmentTemp!!, tagFragmentName)
        } else {
            fragmentTransaction.show(fragmentTemp)
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
    }
}