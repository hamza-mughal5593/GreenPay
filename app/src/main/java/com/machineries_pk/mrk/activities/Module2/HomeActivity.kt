package com.machineries_pk.mrk.activities.Module2

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.MainActivity
import com.machineries_pk.mrk.activities.Module2.ui.home.HomeFragment
import com.machineries_pk.mrk.activities.Module2.ui.home.HomeViewModel
import com.machineries_pk.mrk.activities.Module2.ui.notifications.NotificationsFragment
import com.machineries_pk.mrk.databinding.ActivityHomeBinding
import io.paperdb.Paper

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


      binding.nameUser.text =   Paper.book().read("name","User Name")
        binding.back.setOnClickListener { onBackPressed() }

        binding.popupmenu.setOnClickListener {
            val popup = PopupMenu(this@HomeActivity, binding.popupmenu)
            //Inflating the Popup using xml file
            popup.menuInflater
                .inflate(R.menu.popup_menu, popup.menu)

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener { item ->

                if (item.itemId == R.id.one) {

                    calclut()


                } else if (item.itemId == R.id.logout) {

                    backbtn(true)


                }




                true
            }
            //registering popup with OnMenuItemClickListener

            popup.show() //showing popup mengu
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
    fun backbtn(logout: Boolean) {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.exit_dialog)
        dialog.show()


        var cancel = dialog.findViewById<TextView>(R.id.cancel)
        var exit = dialog.findViewById<TextView>(R.id.exit)
        var data = dialog.findViewById<TextView>(R.id.data)

        if (logout) {
            exit.text = "Logout"
            data.text = "Are you sure you want to Logout?"

        }


        cancel.setOnClickListener {
            dialog.dismiss()
        }

        exit.setOnClickListener {


            dialog.dismiss()

            if (logout) {
                Paper.book().write("email", "")
                finish()
            } else {
                finishAffinity()
            }


        }

    }
    fun calclut() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.exit_dialog)
        dialog.show()


        var cancel = dialog.findViewById<TextView>(R.id.cancel)
        var exit = dialog.findViewById<TextView>(R.id.exit)
        var data = dialog.findViewById<TextView>(R.id.data)


        exit.text = "Yes"
        data.text = "Are you sure you want to re-calculate your carbon footprints?"




        cancel.setOnClickListener {
            dialog.dismiss()
        }

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            dialog.dismiss()


        }

    }
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