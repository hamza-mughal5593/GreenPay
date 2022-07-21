package com.machineries_pk.mrk.activities.NewCode

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.MainActivity
import com.machineries_pk.mrk.activities.Utils.GPSTracker
import com.machineries_pk.mrk.activities.Utils.MySingleton
import com.machineries_pk.mrk.databinding.ActivityLoginBinding
import io.paperdb.Paper
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    var gps: GPSTracker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()

            if (binding.etEmail.text.toString().isNotEmpty()
                && binding.etPassword.text.toString().isNotEmpty()){
                login()
            }else{
                Toast.makeText(
                    this,
                    "Enter valid Email and Password",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.forgetpass.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)

        }
        binding.backBtn.setOnClickListener {
           finish()
        }



        gps = GPSTracker(this@LoginActivity)

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


    }


    private fun login() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)



        dialog.show()

        val RegistrationRequest: StringRequest = object : StringRequest(
            Method.POST,
            "http://www.greensave.co/",
            Response.Listener
            { response ->
                try {

                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    val objects = JSONObject(response)
                    val response_code = objects.getInt("success")
                    if (response_code == 1) {

                        Toast.makeText(
                            this,
                            "Login Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
val data = objects.getJSONObject("data")
val lifestyle = data.getString("lifestyle")
                        Paper.book().write("email",data.getString("email"))
                        Paper.book().write("name",data.getString("name"))
                        Paper.book().write("account_type",data.getString("account_type"))
                        Paper.book().write("profile_image",data.getString("image"))
                        if (lifestyle.isNotEmpty())
                        {
                            Paper.book().write("lifestyle",lifestyle)
                            val intent = Intent(this, CalculateResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }


                    } else {
                        Toast.makeText(
                            this,
                            "Enter valid Email and Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                dialog.dismiss()
            },
            Response.ErrorListener { volleyError ->
                dialog.dismiss()
                var message: String? = null
                when (volleyError) {
                    is NetworkError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ServerError -> {
                        message =
                            "The server could not be found. Please try again after some time!!"
                    }
                    is AuthFailureError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ParseError -> {
                        message = "Parsing error! Please try again after some time!!"
                    }
                    is NoConnectionError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is TimeoutError -> {
                        message = "Connection TimeOut! Please check your internet connection."
                    }
                }
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()

                header["do"]= "login"
                header["apikey"]= "dwamsoft12345"
                header["email"]= binding.etEmail.text.toString()
                header["password"]=binding.etPassword.text.toString()
                header["lat"]= Paper.book().read("latitude", 0.0).toString()
                header["lng"]=Paper.book().read("longitude", 0.0).toString()

                return header
            }

//            override fun getParams(): MutableMap<String, String> {
//                val header: MutableMap<String, String> = HashMap()
//
//                header[""] = ""
//
//                header["do"]="add_user"
//                header["apikey"]="dwamsoft12345"
//                header["name"]=binding.etname.text.toString()
//                header["email"]=binding.etEmail.text.toString()
//                header["password"]=binding.etPassword.text.toString()
//                header["account_type"]=courses[accounttype].toString()
//                header["lat"]= Paper.book().read("latitude", 0.0).toString()
//                header["lng"]=Paper.book().read("longitude", 0.0).toString()
//                return header
//            }
        }
        RegistrationRequest.retryPolicy = DefaultRetryPolicy(
            25000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        MySingleton.getInstance(this).addToRequestQueue(RegistrationRequest)
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