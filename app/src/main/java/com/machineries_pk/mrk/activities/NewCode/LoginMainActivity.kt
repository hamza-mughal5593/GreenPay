package com.machineries_pk.mrk.activities.NewCode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.activities.MainActivity
import com.machineries_pk.mrk.activities.Utils.GPSTracker
import com.machineries_pk.mrk.databinding.ActivityLoginMainBinding
import io.paperdb.Paper

class LoginMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginMainBinding

    var gps: GPSTracker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email =  Paper.book().read("email","")
        if (email.isNotEmpty()){
            val intent = Intent(this, CalculateResultActivity::class.java)
            startActivity(intent)
            return
        }else{
            gps = GPSTracker(this@LoginMainActivity)

            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION

                    ),
                    123
                )


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant
                return
            }else{
                gps?.let { getlatlng(it) }
            }

            binding.login.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }
            binding.register.setOnClickListener {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        }





    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 123) {
                gps?.let { getlatlng(it) }
            }
        }
    }
    private fun getlatlng(gps: GPSTracker) {

        if (gps.canGetLocation()) {
            val latitude = gps.latitude
            val longitude = gps.longitude
            Paper.book().write("latitude", latitude)
            Paper.book().write("longitude", longitude)
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            runOnUiThread { gps.showSettingsAlert() }
        }

    }
}